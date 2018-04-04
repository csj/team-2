import com.codingame.game.BasePlayer
import com.codingame.game.Constants.QUEEN_RADIUS
import com.codingame.game.Constants.TOUCHING_DELTA
import com.codingame.game.ObstacleInput
import java.io.InputStream
import java.io.PrintStream

class Wood2Player(stdin: InputStream, stdout: PrintStream, stderr: PrintStream): BasePlayer(stdin, stdout, stderr) {

  private fun myBarracks(): List<ObstacleInput> = obstacles.filter { it.owner == 0 && it.structureType == 2 }

  init {

    while (true) {
      val (queenLoc, _, _, _, _, obstacles, _, _) = readInputs()

      // strategy:
      // build barracks, melee, anytime our income exceeds our ability to spam units
      // spam melee units forever

      fun getQueenAction(): String {

        val queenTarget = obstacles
          .filter { it.owner == -1 }
          .minBy { it.location.distanceTo(queenLoc) } ?: return "WAIT"

        val maxUnitSpend = (myBarracks().size + 0.5) * 16 //  = 80/5
        val needsBarracks = 20 >= maxUnitSpend

        if (!needsBarracks) return "WAIT"

        return "BUILD ${queenTarget.obstacleId} BARRACKS-MELEE"
      }

      try {
        stdout.println(getQueenAction())
        stdout.println("TRAIN${myBarracks().joinToString("") { " " + it.obstacleId }}")
      } catch (ex: Exception) {
        ex.printStackTrace(stderr)
        throw ex
      }

    }
  }
}
