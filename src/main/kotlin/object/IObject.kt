package `object`

import applcation.ObjectApplication
import habitat.Habitat
import javafx.scene.Group
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import java.util.Random
import java.util.function.Predicate

abstract class IObject : Comparable<IObject> {

    protected fun moveImpl(x : Int, y : Int) {

        if(incrementX == 0)
            incrementX = x
        if(incrementY == 0)
            incrementY = y

        if(imageView.x < 0)
            incrementX = x
        else if(imageView.x > borderX)
            incrementX = -x

        if(imageView.y < 0)
            incrementY = y
        else if(imageView.y > borderY)
            incrementY = -y

        imageView.x += incrementX
        imageView.y += incrementY
    }

    open fun move(){}

    open fun spawn(pane : Pane){
    }

    fun spawnImpl(pane : Pane, name : String){
        val image = Image(ObjectApplication::class.java.getResource(name).toString())
        imageView = ImageView()
        imageView.image = image
        imageView.fitWidth = 50.0
        imageView.fitHeight = 50.0

        borderX = Habitat.fieldWidth + Habitat.fieldOffset
        borderY = Habitat.fieldWidth + Habitat.fieldOffset

        imageView.x = Habitat.fieldWidth  * Random().nextFloat() + Habitat.fieldOffset
        imageView.y = Habitat.fieldHeight  * Random().nextFloat() + Habitat.fieldOffset


        id = Random().nextInt(0,1000)
        pane.children.add(imageView)
    }
    public var id : Int = -1
    public var currentLifeTime = 0f
    public lateinit var imageView : ImageView

    private var incrementX = 0
    private var incrementY = 0

    private var borderX = 0.0
    private var borderY = 0.0

    override fun compareTo(other: IObject): Int {
        return id - other.id
    }
}


class FirstObject : IObject() {


    lateinit var pane: Pane

    override fun move() {
        moveImpl(1,0)
    }

    override fun spawn(paneArg : Pane) {
        pane = paneArg
        super.spawnImpl(pane,"RicardoUpsideDown.jpg")
        currentLifeTime = lifeTime
    }

    companion object Properties{
        public var spawnDelay = 2000.0
        public var spawnChance = 0.8f
        public var lifeTime = 1000f
    }

}

class SecondObject : IObject() {

    lateinit var pane: Pane

    override fun move() {
        moveImpl(0,1)
    }

    override fun spawn(paneArg: Pane) {
        pane = paneArg
        super.spawnImpl(pane,"Ricardo.png")
        currentLifeTime = lifeTime
    }

    companion object Properties{
        public var spawnDelay = 2000.0
        public var spawnChance = 0.8f
        public var lifeTime = 1000f
    }
}