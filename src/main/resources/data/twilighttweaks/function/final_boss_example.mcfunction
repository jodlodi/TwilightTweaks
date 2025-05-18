summon husk ~ ~-1 ~
effect give @e[type= minecraft:husk, sort= nearest, limit= 1] minecraft:fire_resistance 999999 1 true
effect give @e[type= minecraft:husk, sort= nearest, limit= 1] minecraft:invisibility 999999 1 true
effect give @e[type= minecraft:husk, sort= nearest, limit= 1] minecraft:absorption 999999 44 true
effect give @e[type= minecraft:husk, sort= nearest, limit= 1] minecraft:strength 999999 4 true
effect give @e[type= minecraft:husk, sort= nearest, limit= 1] minecraft:speed 999999 2 true
item replace entity @e[type= minecraft:husk, sort= nearest, limit= 1] weapon.mainhand with twilightforest:knightmetal_sword[enchantments={'unbreaking':3}] 1
item replace entity @e[type= minecraft:husk, sort= nearest, limit= 1] armor.head with twilightforest:keepsake_casket 1
item replace entity @e[type= minecraft:husk, sort= nearest, limit= 1] armor.chest with minecraft:netherite_chestplate[enchantments={'blast_protection':1,'thorns':3,'binding_curse':1,'unbreaking':3,'vanishing_curse':1},trim={pattern:raiser,material:"twilightforest:carminite"}] 1
item replace entity @e[type= minecraft:husk, sort= nearest, limit= 1] armor.legs with minecraft:netherite_leggings[enchantments={'swift_sneak':3,'fire_protection':1,'thorns':3,'binding_curse':1,'unbreaking':3,'vanishing_curse':1},trim={pattern:snout,material:"twilightforest:fiery"}] 1
item replace entity @e[type= minecraft:husk, sort= nearest, limit= 1] armor.feet with minecraft:netherite_boots[enchantments={'soul_speed':3,'feather_falling':1,'projectile_protection':1,'thorns':3,'depth_strider':3,'binding_curse':1,'unbreaking':3,'vanishing_curse':1},trim={pattern:wayfinder,material:"twilightforest:carminite"}] 1
execute positioned as @p run summon twilightforest:death_tome ~1.2 ~1 ~1.2 {PersistenceRequired:1,CustomName:"\"Book of Flow\"",CustomNameVisible:0b}
execute positioned as @p run summon twilightforest:death_tome ~1.2 ~1 ~-1.2 {PersistenceRequired:1,CustomName:"\"Book of Form\"",CustomNameVisible:0b}
execute positioned as @p run summon twilightforest:death_tome ~-1.2 ~1 ~-1.2 {PersistenceRequired:1,CustomName:"\"Book of Stone\"",CustomNameVisible:0b}
execute positioned as @p run summon twilightforest:death_tome ~-1.2 ~1 ~1.2 {PersistenceRequired:1,CustomName:"\"Book of Order\"",CustomNameVisible:0b}
effect give @e[type= twilightforest:death_tome, sort= nearest, limit= 4] minecraft:absorption 999999 7 true
effect give @e[type= twilightforest:death_tome, sort= nearest, limit= 4] minecraft:strength 999999 1 true
effect give @e[type= twilightforest:death_tome, sort= nearest, limit= 4] minecraft:speed 999999 5 true
summon minecraft:lightning_bolt ~ ~6 ~
effect give @p minecraft:blindness 5
give @p written_book[written_book_content={pages:['[["",{"text":"The dungeon is still a work in progress!","bold":true},"\\nYou can join the Twilight Forest Discord server to follow the latest updates on this castle and other content at:\\n",{"text":"experiment115.com","underlined":true,"clickEvent":{"action":"open_url","value":"https://discord.com/invite/r6YYMsF"},"hoverEvent":{"action":"show_text","contents":"Click to join discord!"},"color":"blue"}]]'],title:"You win!",author:jodi,generation:0},custom_name='["",{"text":"You win!","italic":false}]',lore=['["",{"text":"Join our discord!","italic":false}]']]