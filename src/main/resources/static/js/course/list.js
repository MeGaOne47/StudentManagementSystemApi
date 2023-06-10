$(document).ready(function () {
  $.ajax({
    url: 'http://localhost:8080/api/v1/courses',
    type: 'GET',
    dataType: 'json',
    success: function (data) {
      let trHTML = '';
      $.each(data, function (i, item) {
        trHTML += '<tr id="course-' + item.id + '">' +
          '<td>' + item.id + '</td>' +
          '<td>' + item.name + '</td>' +
          '<td>' + item.description + '</td>' +
          '<td sec:authorize="hasAnyAuthority(\'ADMIN\')">' +
          '<a href="/courses/edit/' + item.id + '" class="text-primary edit-link" data-id="' + item.id + '">Edit</a> | ' +
          '<a href="/courses/' + item.id + '" class="text-danger" onclick="apiDeleteCourse(' + item.id + '); return false;">Delete</a>' +
          '</td>' +
          '</tr>';
      });
      $('#course-table-body').append(trHTML);

      // Sửa khóa học - Xử lý sự kiện khi nhấp vào liên kết "Edit"
      $('.edit-link').click(function (e) {
        e.preventDefault();
        var courseId = $(this).data('id');
        editCourse(courseId);
      });
    }
  });
});

function editCourse(courseId) {
  window.location.href = '/courses/edit/' + courseId;
}

function apiDeleteCourse(id) {
  if (confirm('Are you sure you want to delete this course?')) {
    $.ajax({
      url: 'http://localhost:8080/api/v1/courses/' + id,
      type: 'DELETE',
      success: function () {
        alert('Course deleted successfully!');
        $('#course-' + id).remove();
      }
    });
  }
}
