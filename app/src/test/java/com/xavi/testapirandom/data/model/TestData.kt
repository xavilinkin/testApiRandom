package com.xavi.testapirandom.data.model

object TestData {

    fun createMockRandomModel(): RandomModel {
        val resultUser = ResultUser(
            gender = "male",
            name = Name("Mr", "John", "Doe"),
            location = Location(
                street = Street(123, "Main Street"),
                city = "Cityville",
                state = "Stateville",
                country = "Countryland",
                postcode = "12345",
                coordinates = Coordinates("40.7128", "-74.0060"),
                timezone = Timezone("-5:00", "EST")
            ),
            email = "john.doe@example.com",
            login = Login(
                uuid = "123456789",
                username = "johndoe",
                password = "password123",
                salt = "salt123",
                md5 = "md5hash",
                sha1 = "sha1hash",
                sha256 = "sha256hash"
            ),
            dob = Dob("1990-01-01", 32),
            registered = Registered("2020-01-01", 2),
            phone = "123-456-7890",
            cell = "987-654-3210",
            id = Id("ID", "12345"),
            picture = Picture("large_url", "medium_url", "thumbnail_url"),
            nat = "US"
        )

        val info = Info("seed123", 1, 1, "1.0")

        return RandomModel(listOf(resultUser), info)
    }
}
