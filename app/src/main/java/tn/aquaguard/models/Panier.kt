package tn.aquaguard.models

data class Panier(
    var listProduits: Array<Product>
) {
    // Override equals and hashCode methods for the 'listProduits' property
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Panier

        if (!listProduits.contentEquals(other.listProduits)) return false

        return true
    }

    override fun hashCode(): Int {
        return listProduits.contentHashCode()
    }
}
