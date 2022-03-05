package applcation

import `object`.*
import habitat.Habitat
import javafx.animation.KeyFrame
import javafx.animation.Timeline
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
import javafx.scene.shape.Rectangle
import javafx.util.Duration
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
        initText()
        initField()
        initKeyHandler(scene)
        setTimers()
    }

    private fun initField() {
        val rectangle = Rectangle(Habitat.fieldWidth,Habitat.fieldHeight)
        rectangle.fill = Color.AQUAMARINE
        rectangle.x = 100.0
        rectangle.y = 100.0

        root.children.add(rectangle)
    }

    private fun initKeyHandler(scene: Scene){

        scene.addEventFilter(KeyEvent.KEY_PRESSED, EventHandler {
            when(it.code){
                KeyCode.B -> startSimulation(root)
                KeyCode.E -> stopSimulation()
                KeyCode.T -> toggleTime()
            }
        }
        )
    }


     private lateinit var firstTimeLine : Timeline
     private lateinit var secondTimeLine : Timeline
     private lateinit var timerTimeline: Timeline
    private var isSimulationStarted = false

    private var secondsTimer = 0L
    private fun setTimers(){
        timerTimeline = Timeline(KeyFrame(Duration.millis(1000.0),{
            secondsTimer++
            counterText.text = secondsTimer.toString()
        }))
        timerTimeline.cycleCount = Timeline.INDEFINITE

        firstTimeLine = Timeline( KeyFrame(Duration.millis(FirstObject.spawnDelay),{
            if(Random().nextFloat() < FirstObject.spawnChance)
                habitat.spawnObject(root,FirstObject::class.java)
        })
        )
        firstTimeLine.cycleCount = Timeline.INDEFINITE

        secondTimeLine = Timeline( KeyFrame(Duration.millis(SecondObject.spawnDelay),{
            if(Random().nextFloat() < SecondObject.spawnChance)
                habitat.spawnObject(root,SecondObject::class.java)
        })
        )
        secondTimeLine.cycleCount = Timeline.INDEFINITE
    }


    private fun startSimulation(root: Group) {
        if(!isSimulationStarted)
        {
            timerTimeline.play()
            firstTimeLine.play()
            secondTimeLine.play()

            isSimulationStarted = true

            numberOfFirstObj.text = "Number of First objects: 0"
            numberOfSecondObj.text = "Number of Second objects: 0"
        }
    }


    private fun stopSimulation(){

        isSimulationStarted = false

        firstTimeLine.stop()
        secondTimeLine.stop()
        timerTimeline.stop()

        displayObjects()
        habitat.destroyObjects(root)
        counterText.text = "0"
        secondsTimer = 0
    }

    private fun displayObjects(){
        var numOfFirstObjects = 0
        var numOfSecondObjects = 0

        for (item in habitat.objects ) {
            if(item is FirstObject)
                numOfFirstObjects++
            else
                numOfSecondObjects++
        }
        numberOfFirstObj.text = "Number of First objects: $numOfFirstObjects"
        numberOfSecondObj.text = "Number of Second objects: $numOfSecondObjects"
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
    private lateinit var numberOfFirstObj : Text
    private lateinit var numberOfSecondObj : Text
    private fun initText(){

        val header = Text("This lab smells like a bullshit.")
        header.y = 50.0
        header.x = 50.0
        header.font = Font.font("Italic",50.0)
        header.fill = Color.RED
        header.opacity = 0.1

        val line = Line(0.0,50.0,Habitat.width,50.0)
        line.stroke = Color.RED

        counterText  = Text("0.0")
        counterText.font = Font.font("Italic",60.0)
        counterText.fill = Color.RED
        counterText.y = 100.0
        counterText.x = 100.0

        numberOfFirstObj = Text("Number of First objects: 0")
        numberOfFirstObj.font = Font.font("Verdana",15.0)
        numberOfFirstObj.fill = Color.CORAL
        numberOfFirstObj.y = 35.0
        numberOfFirstObj.x = 100.0

        numberOfSecondObj = Text("Number of Second objects: 0")
        numberOfSecondObj.font = Font.font("Verdana",15.0)
        numberOfSecondObj.fill = Color.CYAN
        numberOfSecondObj.y = 50.0
        numberOfSecondObj.x = 100.0

        root.children.add(numberOfFirstObj)
        root.children.add(numberOfSecondObj)
        root.children.add(counterText)
        root.children.add(header)
        root.children.add(line)
    }

}

