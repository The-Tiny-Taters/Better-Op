package the_tiny_taters.better_op

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import the_tiny_taters.better_op.command.EditOpCommand
import the_tiny_taters.better_op.command.OpCommand

object Common : ModInitializer {
    override fun onInitialize() {
        println("[Better Op] Improving op command!")

        CommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            dispatcher.root.addChild(OpCommand().register())
            dispatcher.root.addChild(EditOpCommand().register())
        }
    }
}

