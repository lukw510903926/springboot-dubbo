$.fn.extend({

    setForm: function (param) {
        var _this = this;
        _this.find('[name]').each(function () {
            $(this).val(param[this.name])
        });
    },

    getForm: function () {
        var _this = this;
        var param = {};
        _this.find('[name]').each(function () {
            var value = $.trim($(this).val());
            if (value) {
                param[this.name] = value
            }
        });
        return param;
    }
});