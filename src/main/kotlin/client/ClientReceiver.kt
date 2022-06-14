package client
import `object`.FirstObject
import `object`.SecondObject
import org.application.Controller
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.ObjectInputStream
import java.net.Socket
import java.util.*


class ClientReceive(client: Socket,controllerVar: Controller) : Thread() {

    private lateinit var reader : BufferedReader
    var clientSocket: Socket
    var scanner : Scanner
    val controller : Controller
    val objectStream : ObjectInputStream
    val receivedObjects = ArrayList<Any>()
    var clientList = String()
    init {
        clientSocket = client
        scanner = Scanner(clientSocket.getInputStream())
        controller = controllerVar
        objectStream = ObjectInputStream(clientSocket.getInputStream())

    }

    override fun run() {

        reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
        while (true) {
            if(scanner.hasNext()) {
                var msg : String = scanner.nextLine()

                if(msg.equals("#clientlist#",true))
                {
                    var string = String()
                    while (!msg.equals("#msgend#", true))
                    {
                        string += "\n" + msg
                        msg = scanner.nextLine()
                        println(msg)
                        sleep(100)
                    }
                    if(clientList != string){
                        clientList = string
                        controller.cleanConnectedUsers()
                        controller.printCurrentUsers(clientList)
                    }
                }

                if(msg.equals("#lifetime1#",true)) {
                    val string = scanner.nextLine()

                    controller.lifeTimeFirstLabel.text = string
                    controller.lifeTimeFirstSlider.value = string.toDouble()

                    FirstObject.lifeTime = string.toFloat()
                }

                if(msg.equals("#lifetime2#",true)) {
                    val string = scanner.nextLine()

                    controller.lifeTimeSecondSlider.value = string.toDouble()
                    SecondObject.lifeTime = string.toFloat()
                }

                if(msg.equals("#chance1#",true)) {
                    val string = scanner.nextLine()
                    FirstObject.spawnChance = string.toFloat()
                }
                if(msg.equals("#chance2#",true)) {
                    val string = scanner.nextLine()
                    SecondObject.spawnChance = string.toFloat()
                }
                if(msg.equals("#delay1#",true)) {
                    val string = scanner.nextLine()
                    controller.appearingFirstObjDelay.value = string.toDouble()
                    FirstObject.spawnDelay = string.toDouble()
                }

                if(msg.equals("#delay2#",true)) {
                    val string = scanner.nextLine()
                    controller.appearingSecObjDelay.value = string.toDouble()
                    SecondObject.spawnDelay = string.toDouble()
                }
                println(msg)
                sleep(100)
            }
        }
    }

}
