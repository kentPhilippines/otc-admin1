/**
 * Author and copyright: Stefan Haack (https://shaack.com)
 * Repository: https://github.com/shaack/bootstrap-input-spinner
 * License: MIT, see file 'LICENSE'
 */


    // the default editor for parsing and rendering
const I18nEditor = function (props, element) {
        const locale = props.locale || "en-US"

        this.parse = function (customFormat) {
            const numberFormat = new Intl.NumberFormat(locale)
            const thousandSeparator = numberFormat.format(11111).replace(/1/g, '') || '.'
            const decimalSeparator = numberFormat.format(1.1).replace(/1/g, '')
            return parseFloat(customFormat
                .replace(new RegExp(' ', 'g'), '')
                .replace(new RegExp('\\' + thousandSeparator, 'g'), '')
                .replace(new RegExp('\\' + decimalSeparator), '.')
            )
        }

        this.render = function (number) {
            const decimals = parseInt(element.getAttribute("data-decimals")) || 0
            const digitGrouping = !(element.getAttribute("data-digit-grouping") === "false")
            const numberFormat = new Intl.NumberFormat(locale, {
                minimumFractionDigits: decimals,
                maximumFractionDigits: decimals,
                useGrouping: digitGrouping
            })
            return numberFormat.format(number)
        }
    }

let triggerKeyPressed = false
const originalVal = $.fn.val
$.fn.val = function (value) {
    if (arguments.length >= 1) {
        for (let i = 0; i < this.length; i++) {
            if (this[i]["bootstrap-input-spinner"] && this[i].setValue) {
                const element = this[i]
                setTimeout(function () {
                    element.setValue(value)
                })
            }
        }
    }
    return originalVal.apply(this, arguments)
}

