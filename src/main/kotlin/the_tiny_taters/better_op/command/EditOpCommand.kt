package the_tiny_taters.better_op.command

import com.github.p03w.aegis.aegisCommand
import com.mojang.authlib.GameProfile
import com.mojang.brigadier.arguments.BoolArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import net.minecraft.command.argument.GameProfileArgumentType
import net.minecraft.server.OperatorEntry
import net.minecraft.text.LiteralText
import the_tiny_taters.better_op.Context
import the_tiny_taters.better_op.Node
import the_tiny_taters.better_op.mixin.PlayerManagerAccessor

class EditOpCommand {
    fun register(): Node {
        return  aegisCommand("editop") {
            requires {
                it.hasPermissionLevel(3)
            }

            custom("targets", GameProfileArgumentType.gameProfile()) {
                integer("level", min=1, max=4) {
                    executes {
                        editOpCommand(
                            it,
                            GameProfileArgumentType.getProfileArgument(it, "targets").iterator(),
                            IntegerArgumentType.getInteger(it, "level"),
                        )
                    }

                    bool("bypassPlayerLimit") {
                        executes {
                            editOpCommand(
                                it,
                                GameProfileArgumentType.getProfileArgument(it, "targets").iterator(),
                                IntegerArgumentType.getInteger(it, "level"),
                                BoolArgumentType.getBool(it, "bypassPlayerLimit"),
                            )
                        }
                    }
                }
            }
        }
            .build()
    }

    private fun editOpCommand(context: Context, targets: Iterator<GameProfile>, level: Int = 4, bypass: Boolean = false) {
        val manager = context.source.minecraftServer.playerManager

        targets.forEach { target ->
            if (!manager.isOperator(target)) {
                context.source.sendError(
                    LiteralText("§c${target.name} is not an operator")
                )
                return@forEach
            }

            val targetPlayer = manager.getPlayer(target.id)

            if (targetPlayer != null && !targetPlayer.hasPermissionLevel(level)) {
                context.source.sendError(
                    LiteralText("§cCannot set players to a permission level higher than your own")
                )
                return@forEach
            }

            manager.removeFromOperators(target)
            (manager as PlayerManagerAccessor).ops.add(
                OperatorEntry(target, level, bypass) // it's that easy. it's already part of the game.
            )

            if (targetPlayer != null) {
                manager.sendCommandTree(targetPlayer)
            }

            val canBypass = if (!bypass) { "not" } else { "" }
            context.source.sendFeedback(
                LiteralText("§Edited §e${target.name} §ato be a level §e$level §aoperator that §ecan$canBypass §abypass the player limit."),
                true
            )
        }
    }
}