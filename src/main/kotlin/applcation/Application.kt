package applcation

import `object`.*
import habitat.Habitat
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.paint.Color
import javafx.scene.shape.Line
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.stage.Stage
import javafx.event.EventHandler
import javafx.event.EventType
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import java.util.Random
import java.util.Timer
import java.util.TimerTask


class ObjectApplication : Application() {

    private val habitat = Habitat()
    private lateinit var root : Group
    override fun start(stage: Stage) {
        root = Group()
        val scene = Scene(root,Habitat.height,Habitat.width,Color.BLACK)
        val rightCornerImg =
            Image(ObjectApplication::class.java.getResource("Ricardo.png").toString())

        initStage(stage,rightCornerImg,scene)
        initText(root)
        initKeyHandler(root,scene)

        scene.addEventFilter(KeyEvent.KEY_PRESSED, EventHandler {
                when(it.code){
                    KeyCode.B -> startSimulation(root)
                    KeyCode.E -> stopSimulation()
                    KeyCode.T -> toggleTime()
                }

        }
        )
    }

    private lateinit var event : EventHandler<KeyEvent>
    private fun initKeyHandler(root : Group, scene: Scene){

    }

    inner class SpawnerTask (arg1: Group, arg2: Float, private val f: (group : Group,
                                                                       customClass : Class<IObject>) -> Unit,
                                                                       argCustomClass : Class<*>? ) : TimerTask() {
        private val root = arg1
        private val chance = arg2
        private val spawnerFunc = f
        private val customClass = argCustomClass
        override fun run() {
            if(Random().nextFloat() < chance) {
                spawnerFunc(root, customClass as Class<IObject>)
            }

        }
    }

    private lateinit var firstTask : SpawnerTask
    private lateinit var secondTask : SpawnerTask

    private val firstTimer = Timer()
    private val secondTimer = Timer()

    private var secondsTimer = 0L

    private fun startSimulation(root: Group) {
        firstTask = SpawnerTask(root,FirstObject.spawnChance,habitat::spawnObject,FirstObject::class.java)
        secondTask = SpawnerTask(root,SecondObject.spawnChance,habitat::spawnObject,FirstObject::class.java)

        firstTimer.schedule(firstTask,FirstObject.spawnDelay)
        secondTimer.schedule(secondTask,SecondObject.spawnDelay)

        secondsTimer = System.currentTimeMillis()
    }


    private fun stopSimulation(){
        firstTimer.cancel()
        secondTimer.cancel()
        secondsTimer = System.currentTimeMillis() - secondsTimer
        displayObjects()
        habitat.destroyObjects(root)
    }

    private fun displayObjects(){
        var yAxisCounter = 50.0
        for (item in habitat.objects ) {
            val text = Text()
            text.font = Font.font("Italic",30.0)
            text.fill = Color.AQUA
            yAxisCounter += 30.0

            text.y = yAxisCounter
            text.x = 50.0
            root.children.add(text)
        }
    }
    private fun toggleTime(){
        counterText.isVisible = !counterText.isVisible
    }

    private fun initStage(stage : Stage, rightCornerImg : Image, scene : Scene){
        stage.icons.add(rightCornerImg)
        stage.title = "Ricardo exe"
        stage.scene = scene
        stage.isResizable = false
        stage.show()
    }


    private lateinit var counterText : Text
    private fun initText(root: Group){

        val headder = Text("This lab smells like a bullshit.")
        headder.y = 50.0
        headder.x = 50.0
        headder.font = Font.font("Italic",50.0)
        headder.fill = Color.RED
        headder.opacity = 0.2

        val line = Line(0.0,50.0,Habitat.width,50.0)
        line.stroke = Color.RED

        counterText  = Text()
        counterText.font = Font.font("Italic",60.0)
        counterText.fill = Color.ALICEBLUE
        counterText.y = 50.0
        counterText.x = -50.0

        root.children.add(counterText)
        root.children.add(headder)
        root.children.add(line)
    }

}

