rootProject.name = "items"

projects("core", "paper")

/**
 * Renames a set of project IDs.
 *
 * @param names the list of projects to rename
 */
fun projects(vararg names: String) {
    include(*names)

    names.forEach {
        project(":$it").name = "items-$it"
    }
}