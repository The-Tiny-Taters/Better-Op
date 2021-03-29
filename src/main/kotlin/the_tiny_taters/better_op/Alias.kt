package the_tiny_taters.better_op

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import net.minecraft.server.command.ServerCommandSource

typealias Node = LiteralCommandNode<ServerCommandSource>
typealias Context = CommandContext<ServerCommandSource>