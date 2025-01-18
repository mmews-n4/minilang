package de.marcusmews.minilang.typing



enum class Type(val parent: Type?) {
    Any(null),     // Any is the root type
    Unknown(null), // Unknown has no parent
    Real(Any),            // Real is a subtype of Any
    Int(Real),            // Int is a subtype of Real
    Sequence(Any);        // Sequence is a subtype of Any


    /** Returns true if this type is a subtype of the given type */
    fun isSubtypeOf(other: Type): Boolean {
        var current: Type? = this
        while (current != null) {
            if (current == other) return true
            current = current.parent
        }
        return false
    }
}