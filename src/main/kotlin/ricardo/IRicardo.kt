package ricardo

interface IRicardo {

    open fun flex()
    open fun destroy()

}


class FlexerRicardo : IRicardo {

    override fun flex() {
        TODO("Not yet implemented")
    }

    override fun destroy() {
        TODO("Not yet implemented")
    }

    companion object {
        val spawnDelay = 500L
        val spawnChance = 0.8f
    }

}

class DancerRicardo : IRicardo {

    override fun flex() {
        TODO("Not yet implemented")
    }
    override fun destroy() {
        TODO("Not yet implemented")
    }

    companion object {
        val spawnDelay = 1000L
        val spawnChance = 0.5f
    }
}