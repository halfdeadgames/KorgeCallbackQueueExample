import com.soywiz.kds.Queue
import com.soywiz.klock.TimeSpan
import com.soywiz.klock.seconds
import com.soywiz.korev.Key
import com.soywiz.korge.Korge
import com.soywiz.korge.input.keys
import com.soywiz.korge.view.addUpdater
import com.soywiz.korim.color.Colors

suspend fun main() = Korge(width = 512, height = 512, bgcolor = Colors["#2b2b2b"]) {
	val queue = Queue<() -> Unit>()
	val actionTime = 1.seconds
	var currentTime = 0.seconds

	var counter = 0

	keys {
		this.justDown(Key.SPACE) {
			queue.enqueue {
				println("Counter Value: $counter")
				counter++
			}
		}
	}

	addUpdater { dt: TimeSpan ->
		if(queue.isNotEmpty()) {
			currentTime += dt

			if(currentTime >= actionTime) {
				currentTime -= actionTime
				queue.dequeue().invoke()
			}
		} else {
			currentTime = 0.seconds
		}
	}
}