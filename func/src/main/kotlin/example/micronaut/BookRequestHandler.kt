package example.micronaut

import io.micronaut.context.annotation.Value
import io.micronaut.core.annotation.Introspected
import io.micronaut.function.aws.MicronautRequestHandler
import java.util.UUID

@Introspected
class BookRequestHandler : MicronautRequestHandler<Book?, BookSaved?>() {

    @Value("\${stuff.thing}")
    lateinit var thing: String

    override fun execute(input: Book?): BookSaved? {
        return if (input != null) {
            val bookSaved = BookSaved()
            bookSaved.name = input.name
            bookSaved.isbn = UUID.randomUUID().toString()
            bookSaved.thing = thing
            return bookSaved
        } else {
            null
        }
    }
}