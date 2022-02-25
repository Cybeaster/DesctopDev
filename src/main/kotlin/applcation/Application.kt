package applcation

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.paint.Color
import javafx.stage.Stage

class FishApplication : Application() {
    override fun start(stage: Stage) {
        val root = Group()
        val scene = Scene(root,Color.BLACK)

        val rightCornerImg =
            Image(FishApplication::class.java.getResource("Ricardo.png").toString())
        stage.icons.add(rightCornerImg)
        stage.title = "Specific one"
        stage.scene = scene
        stage.show()
    }
}