import java.util.*
import kotlin.collections.ArrayList

class RhymyMainController
    (vararg words: String) {

    private var wordData = arrayListOf<String>()

    //last char count
    private var charCount = mutableMapOf<Char, Int>()

    private var filteredWordsMap = mutableMapOf<Char, ArrayList<String>>()

    init {
        words.forEach {
            wordData.add(it)
        }
    }

    /*
    filter every word to separate list
     */
    private fun filterWords() {
        wordData.forEach {
            if (filteredWordsMap.containsKey(it.last())) {
                filteredWordsMap[it.last()]?.add(it)
            } else {
                filteredWordsMap[it.last()] = arrayListOf(it)
            }
        }
    }

    /*
    calculate last char count in word list
     */
    fun calculateCharCount() {

        wordData.forEach {

            if (charCount.containsKey(it.last())) {
                charCount[it.last()] = charCount[it.last()]!! + 1
            } else {
                charCount[it.last()] = 1
            }
        }
        if (Defines.CHAR_COUNT_DEBUG) {
            println(charCount)
        }
    }

    private fun getRandomWords(list: ArrayList<String>, count: Int): ArrayList<String> {
        val result = arrayListOf<String>()
        for (i in 0 until count) {
            //FIXME: should return same word
            result.add(list[kotlin.random.Random.nextInt(list.size - 1)])
        }

        return result
    }

    private fun charSelector(count: Int = 2, charSet: MutableSet<Char>): List<Char> {
        val firstRandomKey = charSet.random()
        var secondRandomKey = charSet.random()
        while (firstRandomKey.equals(secondRandomKey)) {
            secondRandomKey = charSet.random()
        }

        return listOf(firstRandomKey, secondRandomKey)
    }


    /*
    build and return rhyme words
     */
    fun build(type: RhymeType) {
        calculateCharCount()
        filterWords()

        when (type) {
            RhymeType.AAAB -> {
                if (getDiffCharCount(RhymeType.AABB.toString()) < filteredWordsMap.size) {
                    val randomChars = charSelector(charSet = filteredWordsMap.keys)
                    val a = getRandomWords(filteredWordsMap.getValue(randomChars.first()), 3)
                    val b = getRandomWords(filteredWordsMap.getValue(randomChars.last()), 1)

                    println(a[0])
                    println(a[1])
                    println(a[2])
                    println(b[0])
                }
            }
            RhymeType.ABAB -> {
                val randomChars = charSelector(charSet = filteredWordsMap.keys)
                val a = getRandomWords(filteredWordsMap.getValue(randomChars.first()), 2)
                val b = getRandomWords(filteredWordsMap.getValue(randomChars.last()), 2)

                println(a[0])
                println(b[0])
                println(a[1])
                println(b[1])
            }
            RhymeType.AABB -> {
                val randomChars = charSelector(charSet = filteredWordsMap.keys)
                val a = getRandomWords(filteredWordsMap.getValue(randomChars.first()), 3)
                val b = getRandomWords(filteredWordsMap.getValue(randomChars.last()), 1)

                println(a[0])
                println(a[1])
                println(b[0])
                println(b[1])
            }
        }

    }

    /*
    add word to object
    */
    fun addWord(word: String) {
        wordData.add(word)
    }

    /*
    clear array to reset object
     */
    fun resetRhymy() {
        wordData.clear()
    }
    
    /*
    returns how many different char in a string
     */
    fun getDiffCharCount(data: String) = data.lowercase().toSet().size
}