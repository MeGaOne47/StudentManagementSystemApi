$(document).ready(function() {
  $.ajax({
    url: 'http://localhost:8080/api/v1/classes',
    type: 'GET',
    dataType: 'json',
    success: function(data) {
      let trHTML = '';
      $.each(data, function(i, item) {
        trHTML += '<tr id="class-' + item.id + '">' +
          '<td>' + item.id + '</td>' +
          '<td>' + item.className + '</td>' +
          '<td>' + item.classDescription + '</td>' +
          '<td>' + item.teacher + '</td>' + // Thêm thông tin giáo viên
          '<td sec:authorize="hasAnyAuthority(\'ADMIN\')">' +
          '<a href="/classes/edit/' + item.id + '" class="text-primary edit-link" data-id="' + item.id + '">Edit</a> | ' +
          '<a href="/classes/' + item.id + '" class="text-danger" onclick="apiDeleteClass(' + item.id + '); return false;">Delete</a>' +
          '</td>' +
          '</tr>';
      });
      $('#class-table-body').append(trHTML);

      // Sửa lớp học - Xử lý sự kiện khi nhấp vào liên kết "Edit"
      $('.edit-link').click(function(e) {
        e.preventDefault();
        var classId = $(this).data('id');
        editClass(classId);
      });
    }
  });
});

function editClass(classId) {
  window.location.href = '/classes/edit/' + classId;
}

function apiDeleteClass(id) {
  if (confirm('Are you sure you want to delete this class?')) {
    $.ajax({
      url: 'http://localhost:8080/api/v1/classes/' + id,
      type: 'DELETE',
      success: function() {
        alert('Class deleted successfully!');
        $('#class-' + id).remove();
      }
    });
  }
}
