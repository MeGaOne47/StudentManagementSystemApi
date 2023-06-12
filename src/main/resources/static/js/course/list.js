$(document).ready(function () {
  loadCourses();

  $('#searchInput').on('input', function () {
    var keyword = $(this).val();
    searchCourses(keyword);
  });

  function loadCourses() {
    $.ajax({
      url: 'http://localhost:8080/api/v1/courses',
      type: 'GET',
      dataType: 'json',
      success: function (data) {
        displayCourses(data);
      }
    });
  }

  function searchCourses(keyword) {
    $.ajax({
      url: 'http://localhost:8080/api/v1/courses/search',
      type: 'GET',
      data: { keyword: keyword },
      dataType: 'json',
      success: function (data) {
        displayCourses(data);
      }
    });
  }

  function displayCourses(courses) {
    var tbody = $('#course-table-body');
    tbody.empty();

    if (courses.length === 0) {
      var noDataMessage = $('<tr>').append($('<td colspan="4">').text('No courses found.'));
      tbody.append(noDataMessage);
      return;
    }

    $.each(courses, function (i, course) {
      var row = $('<tr>').attr('id', 'course-' + course.id);

      $('<td>').text(course.id).appendTo(row);
      $('<td>').text(course.name).appendTo(row);
      $('<td>').text(course.description).appendTo(row);

      if (hasAdminAuthority()) {
        var editLink = $('<a>').attr({
          href: '/courses/edit/' + course.id,
          class: 'btn btn-primary edit-link',
          'data-id': course.id
        }).text('Edit');

        var deleteButton = $('<button>').addClass('btn btn-danger delete-link').text('Delete')
          .click(function () {
            deleteCourse(course.id);
          });

        $('<td>').append(editLink, ' ', deleteButton).appendTo(row);
      }

      tbody.append(row);
    });

    // Sửa khóa học - Xử lý sự kiện khi nhấp vào liên kết "Edit"
    $('.edit-link').click(function (e) {
      e.preventDefault();
      var courseId = $(this).data('id');
      editCourse(courseId);
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

  function editCourse(courseId) {
    window.location.href = '/courses/edit/' + courseId;
  }

  function deleteCourse(id) {
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
});
