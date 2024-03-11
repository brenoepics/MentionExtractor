package io.github.brenoepics

import java.util.regex.Pattern

/**
 * This class is responsible for handling mentions in a text. It uses a pattern to identify mentions
 * and has a limit for the maximum number of mentions it can handle.
 */
class MentionExtractor private constructor(builder: Builder) {
    private val pattern: Pattern
    private val maxMentions: Int

    /**
     * Private constructor used by the Builder to create an instance of MentionExtractor.
     */
    init {
        this.pattern = builder.pattern!!.build()
        this.maxMentions = builder.maxMentions
    }

    /**
     * This class is responsible for building an instance of MentionExtractor. It uses the builder
     * pattern to allow for more readable and flexible construction of MentionExtractor instances.
     */
    class Builder {
        internal var pattern: MentionPattern? = null
        var maxMentions: Int = 5

        /**
         * Sets the pattern to be used by the MentionExtractor.
         *
         * @param mentionPattern The pattern to identify mentions.
         * @return The builder instance.
         */
        fun pattern(mentionPattern: MentionPattern?): Builder {
            this.pattern = mentionPattern
            return this
        }

        /**
         * Sets the maximum number of mentions that the MentionExtractor can handle.
         *
         * @param maxMentions The maximum number of mentions.
         * @return The builder instance.
         */
        fun maxMentions(maxMentions: Int): Builder {
            this.maxMentions = maxMentions
            return this
        }

        /**
         * Builds an instance of MentionExtractor using the set pattern and maxMentions.
         *
         * @return A new instance of MentionExtractor.
         */
        fun build(): MentionExtractor {
            return MentionExtractor(this)
        }
    }

    /**
     * Extracts mentions from a given text up to the maximum number of mentions.
     *
     * @param text The text to extract mentions from.
     * @return A set of mentions.
     */
    fun fromString(text: String?): Set<String> {
        return fromString(text, maxMentions)
    }

    /**
     * Extracts mentions from a given text up to the maximum number of mentions.
     *
     * @param text The text to extract mentions from.
     * @param maxMentions The maximum number of mentions to extract.
     * @return A set of mentions.
     */
    fun fromString(text: String?, maxMentions: Int): Set<String> {
        val mentioned: MutableSet<String> = HashSet()
        val matcher = text?.let { pattern.matcher(it) }
        if (matcher != null) {
            while (matcher.find() && mentioned.size < maxMentions) {
                mentioned.add(matcher.group(1))
            }
        }
        return mentioned
    }

    /**
     * Checks if a given text contains a mention.
     *
     * @param text The text to check for mentions.
     * @return True if the text contains a mention, false otherwise
     */
    fun containsMention(text: String?): Boolean {
        val matcher = text?.let { pattern.matcher(it) }
        if (matcher != null) {
            return matcher.find()
        }
        return false
    }

    /**
     * Counts the number of mentions in a given text.
     *
     * @param text The text to count mentions in.
     * @return The number of mentions in the text.
     */
    fun countMentions(text: String?): Int {
        var count = 0
        val matcher = text?.let { pattern.matcher(it) }
        if (matcher != null) {
            while (matcher.find()) {
                count++
            }
        }
        return count
    }
}
