data class Student(var name: String? = null,
                   var index: Int? = null,
                   var address: Address?,
                   var grades: List<Grade>)

data class Grade(var subject: String? = null,
                 var grade: Double? = null)

data class Address(var street: String? = null,
                   var number: Int? = null,
                   var postalCode: String? = null,
                   var city: String? = null)

fun student(block: StudentBuilder.() -> Unit): Student = StudentBuilder().apply(block).build()

fun Student.address(block: Address.() -> Unit): Address = Address().apply(block)

class StudentBuilder {

    var name: String = ""

    var index = 0

    private var address: Address? = null

    private val grades = arrayListOf<Grade>()

    fun grade(block: GradeBuilder.() -> Unit) {
        grades.add(GradeBuilder().apply(block).build())
    }

    fun address(block: AddressBuilder.() -> Unit) {
        address = AddressBuilder().apply(block).build()
    }

    fun build(): Student = Student(name, index, address, grades)

}

class GradeBuilder {
    var subject: String? = null
    var grade: Double? = null

    fun build(): Grade = Grade(subject, grade)
}

class AddressBuilder {

    var street: String = ""
    var number: Int = 0
    var postalCode: String? = null
    var city: String = ""

    fun build(): Address = Address(street, number, postalCode, city)

}

// To run, execute the following commands:
// kotlinc Dsl.kt -include-runtime -d Dsl.jar
// java -jar Dsl.jar
fun main(args: Array<String>) {

    val student = student {
        name = "Ivana"
        index = 25
        address {
            street = "Luxemburger Str."
            number = 10
            postalCode = "13353"
            city = "Berlin"
        }
        grade {
            subject = "csfb"
            grade = 1.0
        }
        grade {
            subject = "fs"
            grade = 1.6
        }
        grade {
            subject = "pdds"
            grade = 1.3
        }
        grade {
            subject = "math"
            grade = 1.0
        }
    }

    print(student)
}