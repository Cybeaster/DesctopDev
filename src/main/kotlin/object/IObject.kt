package `object`

import habitat.Habitat
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import org.application.ObjectApplication
import java.io.Serializable
import java.util.*

enum class ObjectTypes{
    NONE,
    FIRST_OBJECT,
    SECOND_OBJECT
}
fun getObjectType(value : Int) : ObjectTypes{
    when(value) {
        0 -> return ObjectTypes.NONE
        1 -> return ObjectTypes.FIRST_OBJECT
        2 -> return ObjectTypes.SECOND_OBJECT
    }
    return ObjectTypes.NONE
}
data class ObjectDTO(val currentLifeTime : Float,val id : Int,val objType : ObjectTypes, val XCord : Double, val YCord : Double) : Serializable

open class IObject : Comparable<IObject>{


    public open fun getObjectDTO() : ObjectDTO
    { return ObjectDTO(currentLifeTime,id,ObjectTypes.NONE,currentXPos,currentYPos) }

    protected fun setPosition(x : Double, y : Double){
        imageView.y = y
        imageView.x = x
    }

    fun loadFromObjectDTO(DTO: ObjectDTO, pane: Pane) {
        spawn(pane)
        setPosition(DTO.XCord,DTO.YCord)
        id = DTO.id
        currentLifeTime = DTO.currentLifeTime
    }
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

        currentXPos =  imageView.x
        currentYPos = imageView.y
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

    lateinit var imageView : ImageView

    var currentXPos = 0.0
    var currentYPos = 0.0

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
        super.spawnImpl(pane,"/application/RicardoUpsideDown.jpg")
        currentLifeTime = lifeTime
    }

    companion object Properties{
        public var spawnDelay = 2000.0
        public var spawnChance = 0.8f
        public var lifeTime = 1000f
    }

    override fun getObjectDTO(): ObjectDTO
    { return ObjectDTO(currentLifeTime,id,ObjectTypes.FIRST_OBJECT,currentXPos,currentYPos) }

}
class SecondObject : IObject() {

    lateinit var pane: Pane

    override fun move() {
        moveImpl(0,1)
    }

    override fun spawn(paneArg: Pane) {
        pane = paneArg
        super.spawnImpl(pane,"/application/Ricardo.png")
        currentLifeTime = lifeTime
    }

    companion object Properties{
        public var spawnDelay = 2000.0
        public var spawnChance = 0.8f
        public var lifeTime = 1000f
    }

    override fun getObjectDTO(): ObjectDTO {
        return ObjectDTO(currentLifeTime,id,ObjectTypes.SECOND_OBJECT,currentXPos,currentYPos) }
}