package com.mashup.friendlycoding.model

// code block type
const val FOR = 1
const val IF = 2
const val BOOLEAN = 3
const val WHILE = 4

// PlayActivity Signal
val LOST_BOSS_BATTLE = -6
val COMPILE_ERROR = -5
val INFINITE_LOOP = -4
val END_RUN = -3
val START_RUN = -2
val STAGE_INIT = -2
val STAGE_CLEAR = -1

val PRINCESS_MOVE = 0
val PRINCESS_TURN = 1

val ITEM_PICKED = 6
val PLAYER_LOST = 7
val PLAYER_WIN = 8
val CRUSH_ROCK = 10
val KILL_BAT = 11

// type 3 argument

// 보스 1
const val BOSS_FIRE_ATTACK = 0
const val BOSS_WATER_ATTACK = 1

// 보스 2
const val BOSS_JUMPED = 2
const val BOSS_FIST_MOVED = 3

const val BOSS_FIST_DOWN = 4
const val BOSS_PUNCH = 5

// 보스 3
const val BOSS_BLACKHOLE = 6
const val BOSS_GREENHAND = 7
const val IS_BLACKHOLE = 8

const val SHIELD = 0
const val ATTACK = 1

val IS_PICKAXE = 9
val IS_MUSHROOM = 10
val IS_BOOK = 11
val IS_BRANCH = 12
val IS_ROCK = 13
val IS_POISONED = 14
const val IS_ALIVE = 15
const val BASE = 16
val IS_BAT=17
val IS_NOT_POISONED=18
val IS_NOT_BROKEN=19

const val CRUSH_ROCK_COUNT = 2//몇번 쳐야 깨지는지 횟수
const val GET_ROCK_COUNT = 2//얻어야 하는 돌의 갯수


// item numbers
val PATH = 0
val WALL = 1
val CLEAR = 2
val PICKAXE = 3
val MUSHROOM = 4
val BOOK = 5
val BRANCH = 6
val BROKEN_BRANCH = 7
val BOSS = 8
val POISONMUSHROOM = 9
val ROCK = 10
val BAT = 11
