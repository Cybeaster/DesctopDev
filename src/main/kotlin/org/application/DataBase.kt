package org.application

import `object`.ObjectDTO
import `object`.getObjectType
import habitat.Habitat
import java.sql.DriverManager
import java.sql.PreparedStatement

class DataBase {




    fun initDB(){

    }
    companion object {

        val url = "jdbc:h2:~/test"
        var user = "sa"
        var password = ""
        val headder = arrayListOf("ID", "LifeTime","XPos","YPos")
        fun saveIntoDB(habitat: Habitat) {

            val connection = DriverManager.getConnection(url, user, password)
            var queryInsert: String
            var statement = connection.createStatement()

            for (obj in habitat.objects) {
                queryInsert =
                    "INSERT INTO OBJECTS VALUES(${obj.id},${obj.currentLifeTime},${obj.currentXPos},${obj.currentYPos})"
                println(queryInsert)
                statement.execute(queryInsert)
            }
            connection.close()
        }

        fun loadFromDB(controller: Controller) {
            var statement: PreparedStatement
            val connection = DriverManager.getConnection(url, user, password)
            var queryInsert: String

            val objects = ArrayList<ObjectDTO>()
            statement = connection.prepareStatement("SELECT * FROM OBJECTS")
            var resultSet = statement.executeQuery()

            var counter = 0
            while (resultSet.next()) {
                val objectDTO =
                    ObjectDTO(
                        resultSet.getInt("LIFETIME").toFloat(),
                        resultSet.getInt("ID"),
                        getObjectType(resultSet.getInt("OBJTYPE")),
                        resultSet.getInt("XCORD").toDouble(),
                        resultSet.getInt("YCORD").toDouble()
                    )
                objects.add(objectDTO)
                counter++
            }
            connection.close()
            controller.loadDTOObjects(objects)
        }
    }
}