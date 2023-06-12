$(document).ready(function () {
    loadTeachers();

    $('#searchInput').on('input', function () {
        var keyword = $(this).val();
        searchTeachers(keyword);
    });

    function loadTeachers() {
        $.ajax({
            url: 'http://localhost:8080/api/v1/teachers',
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                displayTeachers(data);
            }
        });
    }

    function searchTeachers(keyword) {
        $.ajax({
            url: 'http://localhost:8080/api/v1/teachers/search',
            type: 'GET',
            data: { keyword: keyword },
            dataType: 'json',
            success: function (data) {
                displayTeachers(data);
            }
        });
    }

    function displayTeachers(teachers) {
        var tbody = $('#teacher-table-body');
        tbody.empty();

        if (teachers.length === 0) {
                var noDataMessage = $('<tr>').append($('<td colspan="4">').text('No teachers found.'));
                tbody.append(noDataMessage);
                return;
            }

        $.each(teachers, function (i, teacher) {
            var row = $('<tr>').attr('id', 'teacher-' + teacher.id);

            $('<td>').text(teacher.id).appendTo(row);
            $('<td>').text(teacher.name).appendTo(row);
            $('<td>').text(teacher.age).appendTo(row);
            $('<td>').text(teacher.courseName).appendTo(row);

            if (hasAdminAuthority()) {
                var editLink = $('<a>').attr({
                    href: '/teachers/edit/' + teacher.id,
                    class: 'btn btn-primary edit-link',
                    'data-id': teacher.id
                }).text('Edit');

                var deleteButton = $('<button>').addClass('btn btn-danger delete-link').text('Delete')
                    .click(function () {
                        deleteTeacher(teacher.id);
                    });

                $('<td>').append(editLink, ' ', deleteButton).appendTo(row);
            }

            tbody.append(row);
        });

        // Sửa giáo viên - Xử lý sự kiện khi nhấp vào nút "Edit"
        $('.edit-link').click(function (e) {
            e.preventDefault();
            var teacherId = $(this).data('id');
            editTeacher(teacherId);
        });
    }

    function hasAdminAuthority() {
        // Kiểm tra xem authorities có tồn tại và có quyền ADMIN hay không
        if (currentUser && currentUser.authorities && currentUser.authorities.length > 0) {
            for (var i = 0; i < currentUser.authorities.length; i++) {
                if (currentUser.authorities[i].authority === 'ADMIN') {
                    return true;
                }
            }
        }

        return false;
    }

    function editTeacher(teacherId) {
        window.location.href = '/teachers/edit/' + teacherId;
    }

    function deleteTeacher(id) {
        if (confirm('Are you sure you want to delete this teacher?')) {
            $.ajax({
                url: 'http://localhost:8080/api/v1/teachers/' + id,
                type: 'DELETE',
                success: function () {
                    alert('Teacher deleted successfully!');
                    $('#teacher-' + id).remove();
                }
            });
        }
    }
});