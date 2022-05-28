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


    override fun start(stage: Stage) {
        val loader = FXMLLoader(javaClass.getResource("/ApplicationConfig.fxml"))
        val root : Parent = loader.load()
        val scene = Scene(root,Habitat.width,Habitat.height,Color.BLACK)
        val rightCornerImg =
            Image(ObjectApplication::class.java.getResource("Ricardo.png").toString())
       loader.getController<Controller>().init(stage,rightCornerImg,scene)

    }






}

