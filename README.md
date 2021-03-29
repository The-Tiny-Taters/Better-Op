# Better Op

A simple server mod to improve the `/op` command. Minecraft's default command is limited, in only allowing the user to
add or remove operators, and always with the same default settings. With **Better Op**, you can use optional arguments
when adding an operator to set the operator level and whether they can bypass the player limit. **Better Op** also
introduces the `/editop` command so that you can edit the permission levels of any existing operator (at or below your
-permission level) without ever having to deal with opening `ops.json`

## Commands
There is one new command `/editop`, and arguments added to `/op`

- `editop <targets> [<level>] [<bypassPlayerLimit>]`
  

- `op <targets> [<level>] [<bypassPlayerLimit>]`

*The default values are `level: 4` and `bypassPlayerLimit: false`*



<!-- Banners -->
[![Fabric API Badge][Fabric API Badge]][Fabric API Download]
[![Join the Discord for support][Discord Badge]][Discord Invite]



<!-- Image URLs -->
[Fabric API Badge]: https://i.imgur.com/HabVZJR.png
[Discord Badge]: https://discord.com/assets/bb408e0343ddedc0967f246f7e89cebf.svg

<!-- Hyperlink URLs -->
[Fabric API Download]: https://modrinth.com/mod/fabric-api
[Discord Invite]: https://discord.gg/m5AVR5ywyh
