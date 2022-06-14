package org.application

import `object`.FirstObject
import `object`.ObjectDTO
import `object`.SecondObject
import behaviour.BaseAI
import client.ClientThread
import habitat.Habitat
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.Pane
import javafx.scene.text.Text
import javafx.stage.Stage
import javafx.util.Duration
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.*
import java.lang.Integer.parseInt
import java.util.*

public class Controller {

    @FXML
    lateinit var connectedClientsField : TextArea

    @FXML
    lateinit var consolePane : Pane

    @FXML
    lateinit var console: TextArea

    @FXML
    lateinit var firstThreadPriority : TextField

    @FXML
     lateinit var secondThreadPriority : TextField

    @FXML
     lateinit var firstThreadBox : CheckBox

    @FXML
     lateinit var secondThreadBox : CheckBox

    @FXML
     lateinit var toggleEndWindowButton :Button

    @FXML
     lateinit var toggleTimeButton: Button

    @FXML
     lateinit var stopTopButton: Button

    @FXML
     lateinit var startTopButton: Button

    @FXML
     lateinit var startButton : Button

    @FXML
     lateinit var stopButton: Button

    @FXML
     lateinit var firstAppearingChance : ComboBox<Int>

    @FXML
     lateinit var secondAppearingChance : ComboBox<Int>

    @FXML
     lateinit var appearingFirstObjDelay : Slider

    @FXML
     lateinit var appearingSecObjDelay : Slider

    @FXML
     lateinit var appearingFirstObjLabel : Label

    @FXML
     lateinit var appearingSecObjLabel : Label

    @FXML
     lateinit var lifeTimeFirstSlider : Slider

    @FXML
     lateinit var lifeTimeSecondSlider : Slider

    @FXML
     lateinit var lifeTimeFirstLabel : Label

    @FXML
     lateinit var lifeTimeSecondLabel : Label

    @FXML
     lateinit var viewModeToggle : ToggleButton

    @FXML
     lateinit var hideSimulationTimeButton : Button

    @FXML
     lateinit var timeCounterText : Text

    @FXML
     lateinit var timeInSimulationLabel : Label

    @FXML
     lateinit var numberOfFirstObj : Text

    @FXML
     lateinit var numberOfSecondObj : Text

    @FXML
     lateinit var mainPane : Pane

    @FXML
     lateinit var simulationEndPane : Pane


    private var isSimulationStarted = false
    private var secondsTimer = 0f

    private lateinit var firstTimeLine : Timeline
    private lateinit var secondTimeLine : Timeline
    private lateinit var timerTimeline: Timeline

    private val habitat = Habitat()
    private lateinit var stage : Stage
    private lateinit var scene: Scene
    private var isEndGameWindowDisabled = false
    private var isHiddenSimulationTime = false
    private lateinit var ai : BaseAI
    private val pathToConfig = "E:\\ProgrammingStuff\\Projects\\DesktopDev\\src\\main\\resources\\config.properties"
    private val pathToObjectConfig = "E:\\ProgrammingStuff\\Projects\\DesktopDev\\src\\main\\resources\\objectConfig.properties"
    private val property = Properties()
    private val clientThread = ClientThread(this)
    public var replicateObjects = false
    public var receivedObjects = false
    public var syncSettings = false
    fun startClient(){
        clientThread.serverStart()
    }

    public fun recieveObjects(array: ArrayList<Any>){
        cleanScene()
        for(obj in array)
            habitat.spawnObject(Json.decodeFromString(obj.toString()),mainPane,secondsTimer)
    }
    public fun loadDTOObjects(array: ArrayList<ObjectDTO>){
        cleanScene()
        for (obj in array){
            habitat.spawnObject(obj,mainPane,secondsTimer)
        }
    }
    private fun cleanScene(){
        habitat.destroyObjects(mainPane)
    }

    private fun saveObjects() {
        val file = File(pathToObjectConfig)
        val outputObjectStream = ObjectOutputStream(FileOutputStream(file))
        for(obj in habitat.objects)
            outputObjectStream.writeObject(obj.getObjectDTO())
        outputObjectStream.writeObject(null)
        outputObjectStream.close()
    }
    public fun printCurrentUsers(string: String){
        connectedClientsField.text = string
    }
    public fun cleanConnectedUsers(){
        connectedClientsField.text = ""
    }
    private fun loadObjects(){
        stopImpl(false)
        val file = File(pathToObjectConfig)
        val inputStream = FileInputStream(file)
        if(inputStream.channel.size() != 0L)
        {
            val inputObjStream = ObjectInputStream(inputStream)
            var isObjectValid = true
            while (isObjectValid)
            {
                val obj = inputObjStream.readObject()
                if(obj is ObjectDTO)
                    habitat.spawnObject(obj,mainPane,secondsTimer)
                else
                    isObjectValid = false
            }
        }
        inputStream.close()
        startImpl()
    }

    public fun saveConfig(){
        val property = Properties()

        property.setProperty("isSimulationStarted",isSimulationStarted.toString())

        property.setProperty("FirstObjectAppearingChance",FirstObject.spawnChance.toString())
        property.setProperty("SecondObjectAppearingChance",SecondObject.spawnChance.toString())

        property.setProperty("FirstObjectAppearingDelay",FirstObject.spawnDelay.toString())
        property.setProperty("SecondObjectAppearingDelay",SecondObject.spawnDelay.toString())

        property.setProperty("FirstObjectLifeTime",FirstObject.lifeTime.toString())
        property.setProperty("SecondObjectLifeTime",SecondObject.lifeTime.toString())

        property.setProperty("FirstThreadEnabled",firstThreadBox.isSelected.toString())
        property.setProperty("SecondThreadEnable",secondThreadBox.isSelected.toString())

        property.setProperty("FirstThreadPriority",firstThreadPriority.text)
        property.setProperty("SecondThreadPriority",secondThreadPriority.text)

        val file = File(pathToConfig)
        val outputSteam = FileOutputStream(file)
        property.store(outputSteam,null)
        outputSteam.close()
    }
    private fun loadConfig(){
        stopImpl(false)
        val file = File(pathToConfig)
        val inputStream = FileInputStream(file)
        if(inputStream.read() != -1)
        {
            property.load(inputStream)
            if(property.getProperty("isSimulationStarted").toBoolean())
                startSimulation()

            FirstObject.spawnChance = property.getProperty("FirstObjectAppearingChance").toFloat()
            SecondObject.spawnChance = property.getProperty("SecondObjectAppearingChance").toFloat()

            FirstObject.spawnDelay =  property.getProperty("FirstObjectAppearingDelay").toDouble()
            SecondObject.spawnDelay = property.getProperty("SecondObjectAppearingDelay").toDouble()

            FirstObject.lifeTime = property.getProperty("FirstObjectLifeTime").toFloat()
            SecondObject.lifeTime = property.getProperty("SecondObjectLifeTime").toFloat()


            firstThreadBox.isSelected = property.getProperty("FirstThreadEnabled").toBoolean()
            if(!firstThreadBox.isSelected)
                ai.firstObjectThread.stopThread()

            secondThreadBox.isSelected = property.getProperty("SecondThreadEnable").toBoolean()
            if(!secondThreadBox.isSelected)
                ai.secondObjectThread.stopThread()

            firstThreadPriority.text = property.getProperty("FirstThreadPriority")
            secondThreadPriority.text = property.getProperty("SecondThreadPriority")

            appearingFirstObjLabel.text = property.getProperty("FirstObjectAppearingDelay")
            appearingSecObjLabel.text = property.getProperty("SecondObjectAppearingDelay")

            lifeTimeFirstLabel.text = FirstObject.lifeTime.toString()
            lifeTimeSecondLabel.text = SecondObject.lifeTime.toString()

            lifeTimeFirstSlider.value = FirstObject.lifeTime.toDouble()
            lifeTimeSecondSlider.value = SecondObject.lifeTime.toDouble()


        }
        inputStream.close()
    }

    private fun initStage(rightCornerImg : Image){
        stage.icons.add(rightCornerImg)
        stage.title = "Ricardo exe"
        stage.scene = scene
        stage.isResizable = false
        stage.show()
    }

    private fun initKeyHandler(scene: Scene){
        scene.addEventFilter(KeyEvent.KEY_PRESSED, EventHandler {
            when(it.code){
                KeyCode.B -> onStartButtonClicked(ActionEvent())
                KeyCode.E -> onStopButtonClicked(ActionEvent())
                KeyCode.T -> onHideSimulationTimeButtonClicked(ActionEvent())
            }
        }
        )
    }


    public fun init(stageArg : Stage, rightCornerImg : Image, sceneArg : Scene){
        stage = stageArg
        scene = sceneArg
        initStage(rightCornerImg)
        initKeyHandler(scene)
        setTimers()

        ai = BaseAI(habitat)
        ai.start()
        loadConfig()
       // startClient()
    }

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
        pauseSimulation()

        habitat.destroyObjects(mainPane)
        timeCounterText.text = "0"
        secondsTimer = 0f
    }
    private fun pauseSimulation()
    {
        isSimulationStarted = false

        firstTimeLine.stop()
        secondTimeLine.stop()
        timerTimeline.stop()


        displayObjects()

    }
    private fun updateTimers(){

        firstTimeLine.stop()
        secondTimeLine.stop()

        firstTimeLine.keyFrames.clear()
        secondTimeLine.keyFrames.clear()

        firstTimeLine.keyFrames.add(KeyFrame(Duration.millis(FirstObject.spawnDelay),{
            if(Random().nextFloat() < FirstObject.spawnChance)
                habitat.spawnObject(mainPane,FirstObject::class.java,secondsTimer)
        }))

        secondTimeLine.keyFrames.add(KeyFrame(Duration.millis(SecondObject.spawnDelay),{
            if(Random().nextFloat() < SecondObject.spawnChance)
                habitat.spawnObject(mainPane, SecondObject::class.java,secondsTimer)
        }))
        if(isSimulationStarted)
        {
            firstTimeLine.play()
            secondTimeLine.play()
        }
    }
    private fun setTimers(){

        timerTimeline = Timeline(KeyFrame(Duration.millis(1000.0),{
            secondsTimer++
            timeCounterText.text = secondsTimer.toString()
            habitat.tickDeleteTimer(1000f,mainPane)
        }))

        timerTimeline.cycleCount = Timeline.INDEFINITE

        firstTimeLine = Timeline( KeyFrame(Duration.millis(FirstObject.spawnDelay),{
            if(Random().nextFloat() < FirstObject.spawnChance)
                habitat.spawnObject(mainPane,FirstObject::class.java,secondsTimer)
        })
        )
        firstTimeLine.cycleCount = Timeline.INDEFINITE

        secondTimeLine = Timeline( KeyFrame(Duration.millis(SecondObject.spawnDelay),{
            if(Random().nextFloat() < SecondObject.spawnChance)
                habitat.spawnObject(mainPane, SecondObject::class.java,secondsTimer)
        }))
        secondTimeLine.cycleCount = Timeline.INDEFINITE
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
        numberOfFirstObj.text = "$numOfFirstObjects"
        numberOfSecondObj.text = "$numOfSecondObjects"
    }
    private fun toggleTime(){
        timeCounterText.isVisible = !timeCounterText.isVisible
        timeInSimulationLabel.isVisible = !timeInSimulationLabel.isVisible
    }

    private fun startImpl(){
        startSimulation()
        startButton.isDisable = true
        startTopButton.isDisable = true
        stopButton.isDisable = false
        stopTopButton.isDisable = false

    }
    public fun onStartButtonClicked(e : ActionEvent){
        startImpl()
    }

    private fun stopImpl(spawnEndPane : Boolean)
    {
        pauseSimulation()
        if(!isEndGameWindowDisabled)
        {
            if(spawnEndPane)
                simulationEndPane.isVisible = true
            startTopButton.isDisable = true
            stopTopButton.isDisable = true
            setControllersEnabled(false)
        }
        else
        {
            setControllersEnabled(true)
            startTopButton.isDisable = false
            startButton.isDisable = false
        }
        stopButton.isDisable = true
    }
    public fun onStopButtonClicked(e: ActionEvent){
        stopImpl(true)
    }

    public fun setControllersEnabled(enableControlElems : Boolean){
        viewModeToggle.isDisable = !enableControlElems
        secondAppearingChance.isDisable = !enableControlElems
        firstAppearingChance.isDisable = !enableControlElems
        toggleTimeButton.isDisable = !enableControlElems
        toggleEndWindowButton.isDisable = !enableControlElems
    }

    @FXML
    public fun onToggleEndMenuClicked(e: ActionEvent){

        if(isEndGameWindowDisabled){
            viewModeToggle.text = "Disable end game window"
            isEndGameWindowDisabled = false
        }
        else{
            viewModeToggle.text = "Enable end game window"
            isEndGameWindowDisabled = true
        }
    }
    @FXML
    public fun onFirstAppearingSliderChanged(){
        FirstObject.spawnDelay = appearingFirstObjDelay.value
        appearingFirstObjLabel.text = "${appearingFirstObjDelay.value.toInt()}"
        updateTimers()
    }
    @FXML
    public fun onSecondAppearingSliderChanged(){
        SecondObject.spawnDelay = appearingSecObjDelay.value
        appearingSecObjLabel.text = "${appearingSecObjDelay.value.toInt()}"
        updateTimers()
    }

    @FXML
    public fun onFirstLifeTimeSliderChanged(){
        FirstObject.lifeTime = lifeTimeFirstSlider.value.toFloat()
        lifeTimeFirstLabel.text = "${lifeTimeFirstSlider.value.toInt()}"
    }
    @FXML
    public fun onSecondLifeTimeSliderChanged(){
        SecondObject.lifeTime = lifeTimeSecondSlider.value.toFloat()
        lifeTimeSecondLabel.text = "${lifeTimeSecondSlider.value.toInt()}"
    }

    @FXML
    public fun onFirstChanceSelected(e: ActionEvent){
        FirstObject.spawnChance = firstAppearingChance.selectionModel.selectedItem.toFloat() / 100
    }

    @FXML
    public fun onSecondChanceSelected(e: ActionEvent){
        SecondObject.spawnChance = secondAppearingChance.selectionModel.selectedItem.toFloat() / 100
    }

    @FXML
    public fun onHideSimulationTimeButtonClicked(e:ActionEvent){
        if(isHiddenSimulationTime)
        {
            hideSimulationTimeButton.text = "Hide simulation time"
            isHiddenSimulationTime = false
        }
        else
        {
            hideSimulationTimeButton.text = "Show simulation time"
            isHiddenSimulationTime = true
        }
        toggleTime()

    }
    @FXML
    public fun initialize(){

        firstAppearingChance.items.addAll(10,20,30,40,50,60,70,80,90,100)
        secondAppearingChance.items.addAll(10,20,30,40,50,60,70,80,90,100)
    }

    @FXML
    public fun onEndMenuOkClicked(e: ActionEvent){
        simulationEndPane.isVisible = false

        stopSimulation()
        setControllersEnabled(true)
        startButton.isDisable = false
        startTopButton.isDisable = false

    }

    @FXML
    public fun onEndMenuDenyClicked(e: ActionEvent){
        simulationEndPane.isVisible = false
        startSimulation()
        startButton.isDisable = true
        startTopButton.isDisable = true
        stopButton.isDisable = false
        stopTopButton.isDisable = false

        setControllersEnabled(true)
    }

    @FXML
    public fun onFirstCheckBoxSwitched(e: ActionEvent) {
        if(!firstThreadBox.isSelected)
            ai.firstObjectThread.stopThread()
        else
            ai.firstObjectThread.startThread()

    }

    @FXML
    public  fun onSecondCheckBoxSwitched(e: ActionEvent) {
        if(!secondThreadBox.isSelected)
            ai.secondObjectThread.stopThread()
        else
            ai.secondObjectThread.startThread()
    }

    private fun setPriority(textField : TextField){
        var priority = 1
        try {
            priority = parseInt(textField.text)
        } catch (e: NumberFormatException) {
            textField.text = "1"
        }
        if(priority !in 1..10)
        {
            priority = 10
            textField.text = "10"
        }
    }
    @FXML
    public fun OnFirstPriorityBlock(e: ActionEvent){
        setPriority(firstThreadPriority)
    }

    @FXML
    public fun OnSecondPriorityBlock(e: ActionEvent){
        setPriority(secondThreadPriority)
    }
    @FXML
    public fun OnSaveClicked(e :ActionEvent){
        saveObjects()
    }

    @FXML
    public fun OnLoadClicked(e :ActionEvent){
        loadObjects()
    }

    @FXML
    public fun onConsoleButtonClicked(e : ActionEvent){
        consolePane.isVisible = !consolePane.isVisible
    }

    private val firstCommand = "SetFirstObjLifeTime"
    private val secondCommand = "SetSecondObjLifeTime"


    @FXML
    private fun onCommandTyped(event : KeyEvent){
        if(event.code == KeyCode.ENTER){
            var commandSubstring = ""
            var valueSubstring = ""
            val consoleText = console.text
            for (char in consoleText)
            {
                if(commandSubstring == firstCommand || commandSubstring == secondCommand) {
                    if(char.isDigit())
                        valueSubstring += char
                }
                else
                {
                    if(char.toString() != " ") {
                        commandSubstring += char
                    }
                }
            }

            when (commandSubstring) {
                firstCommand -> {
                    FirstObject.lifeTime = valueSubstring.toFloat()
                }
                secondCommand -> {
                    SecondObject.lifeTime = valueSubstring.toFloat()
                }
                else -> {
                    print("You typed invalid command!")
                    console.text = ""
                    return
                }
            }
            console.text = ""
            console.text =
                "Current lifeTime of the first object type: ${FirstObject.lifeTime} \n" +
                "Current lifeTime of the second object type: ${SecondObject.lifeTime} \n"

            lifeTimeFirstLabel.text = FirstObject.lifeTime.toString()
            lifeTimeSecondLabel.text = SecondObject.lifeTime.toString()

            lifeTimeFirstSlider.value = FirstObject.lifeTime.toDouble()
            lifeTimeSecondSlider.value = SecondObject.lifeTime.toDouble()
        }
    }
    @FXML
    fun sendObjects(e: ActionEvent){
        syncSettings = true
    }

    @FXML
    fun saveObjectsToDB(e: ActionEvent){
        DataBase.saveIntoDB(habitat)
    }

    @FXML
    fun loadObjectsFromDB(e: ActionEvent){
       DataBase.loadFromDB(this)
    }
}