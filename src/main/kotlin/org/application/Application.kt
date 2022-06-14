package org.application

import habitat.Habitat
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.paint.Color
import javafx.stage.Stage

class ObjectApplication : Application() {

    lateinit var controller: Controller
    override fun start(stage: Stage) {
        val loader = FXMLLoader(javaClass.getResource("/ApplicationConfig.fxml"))
        val root : Parent = loader.load()
        val scene = Scene(root,Habitat.width,Habitat.height,Color.BLACK)

        val rightCornerImg =
            Image(ObjectApplication::class.java.getResource("/application/Ricardo.png").toString())
        controller = loader.getController<Controller>()
        controller.init(stage,rightCornerImg,scene)
    }

    override fun stop() {
        controller.saveConfig()
    }






}

