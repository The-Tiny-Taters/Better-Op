package the_tiny_taters.better_op.command

import com.github.p03w.aegis.aegisCommand
import com.mojang.authlib.GameProfile
import com.mojang.brigadier.arguments.BoolArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.command.argument.GameProfileArgumentType
import net.minecraft.server.OperatorEntry
import net.minecraft.server.command.CommandManager
import net.minecraft.text.LiteralText
import the_tiny_taters.better_op.Context
import the_tiny_taters.better_op.Node
import the_tiny_taters.better_op.mixin.PlayerManagerAccessor

class OpCommand {
    fun register(): Node {
        return  aegisCommand("op") {
            requires {
                it.hasPermissionLevel(3)
            }

            gameProfile("targets") {
                integer("level", min=1, max=4) {
                    executes {
                        betterOpCommand(
                            it,
                            GameProfileArgumentType.getProfileArgument(it, "targets").iterator(),
                            IntegerArgumentType.getInteger(it, "level"),
                        )
                    }

                    bool("bypassPlayerLimit") {
                        executes {
                            betterOpCommand(
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

    private fun betterOpCommand(context: Context, targets: Iterator<GameProfile>, level: Int = 4, bypass: Boolean = false) {
        val manager = context.source.minecraftServer.playerManager

        targets.forEach { target ->
            if (manager.isOperator(target)) {
                context.source.sendError(
                    LiteralText("§cNothing changed. ${target.name} already is an operator")
                )
                return@forEach
            }

            (manager as PlayerManagerAccessor).ops.add(
                OperatorEntry(target, level, bypass) // it's that easy. it's already part of the game.
            )

            val targetPlayer = manager.getPlayer(target.id)

            if (targetPlayer != null) {
                manager.sendCommandTree(targetPlayer)
            }

            val canBypass = if (!bypass) { "not" } else { "" }
            context.source.sendFeedback(
                LiteralText("§aAdded §e${target.name} §aas a level §e$level §aoperator that §ecan$canBypass §abypass the player limit."),
                true
            )
        }
    }
}