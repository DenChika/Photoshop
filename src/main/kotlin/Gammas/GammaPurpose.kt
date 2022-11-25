package Gammas

import Configurations.AppConfiguration

enum class GammaPurpose {
    Assign {
        override fun ApplyMode(modes: GammaModes) {
            AppConfiguration.Gamma.AssignMode = modes
        }

        override fun GetCustomValue(): Float {
            return AppConfiguration.Gamma.AssignCustomValue
        }

        override fun ChangeCustomValue(value: Float) {
            AppConfiguration.Gamma.AssignCustomValue = value
        }

        override fun Expanded(): Boolean {
            return AppConfiguration.Gamma.assignExpanded.value
        }

        override fun Selected(): String {
            return AppConfiguration.Gamma.AssignMode.GetName()
        }

        override fun Hide() {
            AppConfiguration.Gamma.assignExpanded.value = false
        }

        override fun Show() {
            AppConfiguration.Gamma.assignExpanded.value = true
        }

        override fun HideTextField() {
            AppConfiguration.Gamma.assignTextFieldHidden.value = true
        }

        override fun ShowTextField() {
            AppConfiguration.Gamma.assignTextFieldHidden.value = false
        }
    },
    Convert {
        override fun ApplyMode(modes: GammaModes) {
            AppConfiguration.Gamma.ConvertMode = modes
        }

        override fun GetCustomValue(): Float {
            return AppConfiguration.Gamma.ConvertCustomValue
        }

        override fun ChangeCustomValue(value: Float) {
            AppConfiguration.Gamma.ConvertCustomValue = value
        }

        override fun Expanded(): Boolean {
            return AppConfiguration.Gamma.convertExpanded.value
        }

        override fun Selected(): String {
            return AppConfiguration.Gamma.ConvertMode.GetName()
        }

        override fun Hide() {
            AppConfiguration.Gamma.convertExpanded.value = false
        }

        override fun Show() {
            AppConfiguration.Gamma.convertExpanded.value = true
        }

        override fun HideTextField() {
            AppConfiguration.Gamma.convertTextFieldHidden.value = true
        }

        override fun ShowTextField() {
            AppConfiguration.Gamma.convertTextFieldHidden.value = false
        }
    };
    abstract fun ApplyMode(modes: GammaModes)
    abstract fun GetCustomValue() : Float
    abstract fun ChangeCustomValue(value: Float)
    abstract fun Expanded() : Boolean
    abstract fun Selected() : String
    abstract fun Hide()
    abstract fun Show()

    abstract fun HideTextField()
    abstract fun ShowTextField()
}