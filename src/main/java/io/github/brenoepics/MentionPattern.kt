package io.github.brenoepics

import java.util.regex.Pattern

/**
 * This class is used to build different types of patterns for mentions. A mention is a way to call
 * out someone in a conversation, usually prefixed with '@'.
 */
class MentionPattern {
    private var pattern: Pattern

    /** The default constructor initializes the pattern to match any word character after '@'.  */
    init {
        this.pattern = compile("@(\\w+)")
    }

    /**
     * This method modifies the pattern to include special characters. The pattern will match any
     * alphanumeric character or hyphen after '@', but not at the start or end of a word.
     *
     * @return this MentionPattern instance
     */
    fun withSpecialChars(): MentionPattern {
        this.pattern = compile("@([A-Za-z0-9._%-]+)")
        return this
    }

    /**
     * This method modifies the pattern to match email-like mentions. The pattern will match any
     * string that looks like an email address after '@'.
     *
     * @return this MentionPattern instance
     */
    fun withEmailLike(): MentionPattern {
        this.pattern = compile("@([A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4})")
        return this
    }

    /**
     * This method modifies the pattern to include underscores. The pattern will match any
     * alphanumeric character or underscore after '@'.
     *
     * @return this MentionPattern instance
     */
    fun withUnderscores(): MentionPattern {
        this.pattern = compile("@([A-Za-z0-9_]+)")
        return this
    }

    /**
     * This method resets the pattern to the default. The pattern will match any word character after
     * '@'.
     *
     * @return this MentionPattern instance
     */
    fun defaultPattern(): MentionPattern {
        this.pattern = compile("@(\\w+)")
        return this
    }

    /**
     * This method sets a custom pattern for the MentionPattern instance.
     *
     * @param pattern the custom pattern to be used
     * @return this MentionPattern instance
     */
    fun customPattern(pattern: String): MentionPattern {
        this.pattern = compile(pattern)
        return this
    }

    /**
     * This method sets the pattern to be case-sensitive.
     *
     * @return this MentionPattern instance
     */
    private fun compile(pattern: String): Pattern {
        return Pattern.compile(pattern)
    }

    /**
     * This method builds and returns the current pattern.
     *
     * @return the current pattern
     */
    fun build(): Pattern {
        return this.pattern
    }
}
