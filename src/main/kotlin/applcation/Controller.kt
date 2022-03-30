package applcation

import `object`.FirstObject
import `object`.SecondObject
import habitat.Habitat
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Slider
import javafx.scene.control.ToggleButton
import javafx.scene.text.Text
import javafx.util.Duration
import java.util.*

public class Controller {

    @FXML
    private lateinit var startButton : Button

    @FXML
    private lateinit var stopButton: Button

    @FXML
    private lateinit var appearingFrequency : ComboBox<Integer>

    @FXML
    private lateinit var appearingFirstObjDelay : Slider

    @FXML
    private lateinit var appearingSecObjDelay : Slider

    @FXML
    private lateinit var appearingFirstObjText : Slider

    @FXML
    private lateinit var appearingSecObjText : Slider

    @FXML
    private lateinit var viewModeToggle : ToggleButton

    @FXML
    private lateinit var hideSimulationTimeButton : Button

    @FXML
    private lateinit var counterText : Text

    @FXML
    private lateinit var numberOfFirstObj : Text

    @FXML
    private lateinit var numberOfSecondObj : Text

    private var currentFirstObjDelay = 0
    private var currentAppearingFreq = 0

    private var isSimulationStarted = false
    private var secondsTimer = 0L

    private lateinit var firstTimeLine : Timeline
    private lateinit var secondTimeLine : Timeline
    private lateinit var timerTimeline: Timeline

    private val habitat = Habitat()

    private fun startSimulation() {
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

        /*isSimulationStarted = false

        firstTimeLine.stop()
        secondTimeLine.stop()
        timerTimeline.stop()

        displayObjects()
        habitat.destroyObjects(root)
        counterText.text = "0"
        secondsTimer = 0*/
    }

  /*  private fun setTimers(){
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
                habitat.spawnObject(root, SecondObject::class.java)
        })
        )
        secondTimeLine.cycleCount = Timeline.INDEFINITE
    }*/

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


    public fun onStartButtonClicked(e : ActionEvent){

    }
    public fun onStopButtonClicked(e: ActionEvent){

    }
    public fun onHideSimulationButtonClicked(e:ActionEvent){

    }

}