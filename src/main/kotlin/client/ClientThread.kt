package client
import org.application.Controller
import java.io.IOException
import java.net.Socket

class ClientThread constructor(controllerVar: Controller) : Thread() {

    val controller : Controller
    init {
        controller = controllerVar
    }

    override fun run() {
        try {
            serverStart()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    fun serverStart() {
        val clientSocket = Socket("localhost", 8000)

        val clientSend = ClientSender(clientSocket,controller)
        clientSends.add(clientSend)
        clientSend.start()

        val clientReceive = ClientReceive(clientSocket,controller)
        clientReceives.add(clientReceive)
        clientReceive.start()
    }

    companion object {
        var clientSends = ArrayList<ClientSender>()
        var clientReceives = ArrayList<ClientReceive>()
    }
}
