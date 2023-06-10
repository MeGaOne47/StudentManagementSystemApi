$(document).ready(function () {
  $.ajax({
    url: 'http://localhost:8080/api/v1/students',
    type: 'GET',
    dataType: 'json',
    success: function (data) {
      let trHTML = '';
      $.each(data, function (i, item) {
        trHTML += '<tr id="student-' + item.id + '">' +
          '<td>' + item.id + '</td>' +
          '<td>' + item.name + '</td>' +
          '<td>' + item.age + '</td>' +
          '<td>' + item.courseName + '</td>' +
          '<td>';

        if (hasAdminAuthority()) {
          trHTML += '<a href="/students/edit/' + item.id + '" class="text-primary edit-link" data-id="' + item.id + '">Edit</a> | ';
          trHTML += '<a href="/students/' + item.id + '" class="text-danger" onclick="apiDeleteStudent(' + item.id + '); return false;">Delete</a>';
        }

        trHTML += '</td>' +
          '</tr>';
      });
      $('#student-table-body').append(trHTML);

      // Sửa học viên - Xử lý sự kiện khi nhấp vào liên kết "Edit"
      $('.edit-link').click(function (e) {
        e.preventDefault();
        var studentId = $(this).data('id');
        editStudent(studentId);
      });
    }
  });
});

function hasAdminAuthority() {
  // Thực hiện kiểm tra quyền truy cập của người dùng
  // Ví dụ: Kiểm tra xem người dùng có quyền admin hay không
  var isAdmin = false;

  // Thực hiện logic kiểm tra quyền truy cập thực tế
  // Ví dụ: Kiểm tra thông qua dữ liệu người dùng hiện tại hoặc thông qua một yêu cầu AJAX khác

  // Ví dụ: Nếu người dùng có quyền admin, gán biến isAdmin = true
  if (currentUser && currentUser.authorities && currentUser.authorities.length > 0) {
    for (var i = 0; i < currentUser.authorities.length; i++) {
      if (currentUser.authorities[i].authority === 'ADMIN') {
        isAdmin = true;
        break;
      }
    }
  }

  return isAdmin;
}

function editStudent(studentId) {
  window.location.href = '/students/edit/' + studentId;
}

function apiDeleteStudent(id) {
  if (confirm('Are you sure you want to delete this student?')) {
    $.ajax({
      url: 'http://localhost:8080/api/v1/students/' + id,
      type: 'DELETE',
      success: function () {
        alert('Student deleted successfully!');
        $('#student-' + id).remove();
      }
    });
  }
}
