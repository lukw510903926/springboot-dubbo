$.namespace("boot.template");

$(function () {
    boot.template.init();
    $('#addBtn').on('click', boot.template.addBtn);
});
boot.template = {

    init: function () {
        var user = boot.template.initUser();
        $('#template').setForm(user);
        $('#textarea').val(JSON.stringify($('#template').getForm()));
    },

    addBtn: function () {
        $('#textarea').val(JSON.stringify($('#template').getForm()));
        boot.template.init();
    },

    initUser: function () {
        return {
            name: "姓名" + Math.floor(Math.random() * 60 + 10),
            username: Date.now()
        };
    }
};