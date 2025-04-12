package com.cericatto.spacecraftflipcards.data.model

data class Astros(
	val people : List<CraftName> = initCraftNames(),
	val number: Int = 12,
	val message: String = "success",
)

fun initCraftNames() = listOf(
	CraftName(
		craft = "ISS",
		name = "Oleg Kononenko"
	),
	CraftName(
		craft = "ISS",
		name = "Nikolai Chub"
	),
	CraftName(
		craft = "ISS",
		name = "Tracy Caldwell Dyson"
	),
	CraftName(
		craft = "ISS",
		name = "Matthew Dominick"
	),
	CraftName(
		craft = "ISS",
		name = "Michael Barratt"
	),
	CraftName(
		craft = "ISS",
		name = "Jeanette Epps"
	),
	CraftName(
		craft = "ISS",
		name = "Alexander Grebenkin"
	),
	CraftName(
		craft = "ISS",
		name = "Butch Wilmore"
	),
	CraftName(
		craft = "ISS",
		name = "Sunita Williams"
	),
	CraftName(
		craft = "Tiangong",
		name = "Li Guangsu"
	),
	CraftName(
		craft = "Tiangong",
		name = "Li Cong"
	),
	CraftName(
		craft = "Tiangong",
		name = "Ye Guangfu"
	)
)