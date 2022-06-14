package habitat
import `object`.*
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import java.util.*


class Habitat {

    fun destroyObjects(pane: Pane){

        synchronized(objects) {
            for (item in pane.children){
                if(item is ImageView){
                    item.image = null
                }
            }
            objects.clear()
        }
    }

    fun removeObjectFromPanel(obj : IObject, pane: Pane){
        for (paneObj in pane.children){
            if(obj.imageView == paneObj)
            {
                pane.children.remove(paneObj)
                return
            }

        }
    }

    private fun addObjectToList(obj: IObject,currentTime : Float)
    {
        synchronized(objects){
            objects.add(obj)
            objectIds.add(obj.id)
            objectTimeBirth[obj] = currentTime
        }
    }
     public fun spawnObject(pane : Pane, clazz: Class<out IObject>, currentTime : Float) {
        val newRicardo = clazz.getConstructor().newInstance()
         newRicardo.spawn(pane)
         addObjectToList(newRicardo,currentTime)
    }
    public fun spawnObject(DTO : ObjectDTO,pane: Pane,currentTime : Float){
        when(DTO.objType){
            ObjectTypes.FIRST_OBJECT ->
            {
                val firstObject = FirstObject()
                firstObject.loadFromObjectDTO(DTO,pane)
                addObjectToList(firstObject,currentTime)
            }
            ObjectTypes.SECOND_OBJECT ->
            {
                val secondObject = SecondObject()
                secondObject.loadFromObjectDTO(DTO,pane)
                addObjectToList(secondObject,currentTime)
            }
        }
    }
    public fun tickDeleteTimer(deltaTime : Float,pane: Pane){
        for(currentObj in objects){
            currentObj.currentLifeTime -= deltaTime
            if(currentObj.currentLifeTime <= 0){

                synchronized(objects){
                    removeObjectFromPanel(currentObj,pane)
                    objectTimeBirth.remove(currentObj)
                    objectIds.remove(currentObj.id)
                    objects.remove(currentObj)
                }

            }
        }
    }

    fun update() {

    }

    companion object {
        const val width = 1000.0
        const val height = 800.0
        const val fieldOffset = 100.0
        const val fieldWidth = 500.0
        const val fieldHeight = 500.0
    }

    public var objects  = LinkedList<IObject>()
    public var objectIds = HashSet<Int>()
    public var objectTimeBirth = TreeMap<IObject,Float>()
}