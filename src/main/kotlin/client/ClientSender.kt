package client

import `object`.FirstObject
import `object`.SecondObject
import org.application.Controller
import java.io.ObjectOutputStream
import java.io.PrintWriter
import java.net.Socket


class ClientSender(client: Socket,controllerVar: Controller) : Thread() {

    var clientSocket: Socket
    val objStream : ObjectOutputStream
    val printWriter : PrintWriter
    val controller : Controller
    init {
        clientSocket = client
        controller = controllerVar
        printWriter = PrintWriter(client.getOutputStream())
        objStream = ObjectOutputStream(client.getOutputStream())
    }

    override fun run() {

        while (true){
            if(controller.syncSettings) {

                printWriter.println("#lifetime1#")
                printWriter.flush()

                printWriter.println("${FirstObject.lifeTime}")
                printWriter.flush()

                printWriter.println("#lifetime2#")
                printWriter.flush()

                printWriter.println("${SecondObject.lifeTime}")
                printWriter.flush()

                printWriter.println("#chance1#")
                printWriter.flush()

                printWriter.println("${FirstObject.spawnChance}")
                printWriter.flush()

                printWriter.println("#chance2#")
                printWriter.flush()

                printWriter.println("${SecondObject.spawnChance}")
                printWriter.flush()

                printWriter.println("#delay1#")
                printWriter.flush()

                printWriter.println("${FirstObject.spawnDelay}")
                printWriter.flush()

                printWriter.println("#delay2#")
                printWriter.flush()

                printWriter.println("${SecondObject.spawnDelay}")
                printWriter.flush()
                controller.syncSettings = false
            }
            sleep(100)
        }
    }
    fun sendSettings(){


    }
}

