$(document).ready(function () {
    $.ajax({
        url: 'http://localhost:8080/api/v1/teachers',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            let trHTML = '';
            $.each(data, function (i, item) {
                trHTML += '<tr id="teacher-' + item.id + '">' +
                    '<td>' + item.id + '</td>' +
                    '<td>' + item.name + '</td>' +
                    '<td>' + item.age + '</td>' +
                    '<td>' + item.courseName + '</td>' +
                    '<td>';

                if (hasAdminAuthority()) {
                    trHTML += '<a href="/teachers/edit/' + item.id + '" class="text-primary edit-link" data-id="' + item.id + '">Edit</a> | ';
                    trHTML += '<a href="/teachers/' + item.id + '" class="text-danger" onclick="apiDeleteTeacher(' + item.id + '); return false;">Delete</a>';
                }

                trHTML += '</td>' +
                    '</tr>';
            });
            $('#teacher-table-body').append(trHTML);

            // Sửa giáo viên - Xử lý sự kiện khi nhấp vào liên kết "Edit"
            $('.edit-link').click(function (e) {
                e.preventDefault();
                var teacherId = $(this).data('id');
                editTeacher(teacherId);
            });
        }
    });
});

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

function apiDeleteTeacher(id) {
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
