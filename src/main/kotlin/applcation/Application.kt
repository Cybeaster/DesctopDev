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
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.shape.Rectangle
import javafx.util.Duration
import java.util.Random

class ObjectApplication : Application() {


    override fun start(stage: Stage) {

        val root : Parent = FXMLLoader.load(javaClass.getResource("/Application.fxml"))
        val scene = Scene(root,Habitat.width,Habitat.height,Color.BLACK)
        val rightCornerImg =
            Image(ObjectApplication::class.java.getResource("Ricardo.png").toString())

      initStage(stage,rightCornerImg,scene)
        /*
       initText()
       initField()
       initKeyHandler(scene)
       setTimers()*/
    }


    private fun initStage(stage : Stage, rightCornerImg : Image, scene : Scene){
        stage.icons.add(rightCornerImg)
        stage.title = "Ricardo exe"
        stage.scene = scene
        stage.isResizable = false
        stage.show()
    }
    private fun initKeyHandler(scene: Scene){
/*
        scene.addEventFilter(KeyEvent.KEY_PRESSED, EventHandler {
            when(it.code){
                KeyCode.B -> startSimulation()
                KeyCode.E -> stopSimulation()
                KeyCode.T -> toggleTime()
            }
        }
        )*/
    }






}

