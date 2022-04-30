package behaviour
import `object`.FirstObject
import `object`.SecondObject
import habitat.Habitat
import java.lang.Thread.sleep

abstract class ObjectThread : Runnable{

    @Synchronized fun stopThread(){
        isRunning = false
    }

    @Synchronized fun startThread(){
        isRunning = true
    }

    public var isRunning = true
}

class FirstObjectThread constructor(habitatVal: Habitat) : ObjectThread(){

    private val habitat = habitatVal
    override fun run() {
        while (true)
        {
            while (isRunning)
            {
                sleep(10)
                for (obj in habitat.objects) {
                    if (obj is SecondObject)
                        obj.move()
                }
            }
            sleep(1000)
            print("...")
        }

    }
}

class SecondObjectThread constructor(habitatVal: Habitat) : ObjectThread() {

    private val habitat = habitatVal
    override fun run() {
        while (true)
        {
            while (isRunning)
            {
                sleep(10)
                for (obj in habitat.objects) {
                    if (obj is SecondObject)
                        obj.move()
                }
            }
            sleep(1000)
            print("...")
        }
    }
}



class BaseAI constructor(habitatVal: Habitat){

    val firstObjectThread = FirstObjectThread(habitatVal)
    val secondObjectThread = SecondObjectThread(habitatVal)

    fun start(){
        Thread(firstObjectThread).start()
        Thread(secondObjectThread).start()
    }


}