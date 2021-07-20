package ru.myitschool.nasa_bootcamp.data.model.nasa.asteroids

fun ArrayList<Asteroid>.toDatabaseModel():Array<AsteroidEntity>{

    return map{
        AsteroidEntity(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.close_approach_date,
            absoluteMagnitude = it.absolute_magnitude,
            estimatedDiameter = it.estimated_diameter,
            relativeVelocity = it.relative_velocity,
            distanceFromEarth = it.distance_from_earth,
            isPotentiallyHazardous = it.is_dangerous
        )
    }.toTypedArray()
}

fun List<AsteroidEntity>.toDomainModel():List<Asteroid>{
    return map{
        Asteroid(
            id = it.id,
            codename = it.codename,
            close_approach_date = it.closeApproachDate,
            absolute_magnitude = it.absoluteMagnitude,
            estimated_diameter = it.estimatedDiameter,
            relative_velocity = it.relativeVelocity,
            distance_from_earth = it.distanceFromEarth,
            is_dangerous = it.isPotentiallyHazardous
        )
    }
}
