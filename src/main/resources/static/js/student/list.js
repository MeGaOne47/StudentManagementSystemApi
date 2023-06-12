$(document).ready(function () {
  loadStudents();

  $('#searchInput').on('input', function () {
    var keyword = $(this).val();
    searchStudents(keyword);
  });

  function loadStudents() {
    $.ajax({
      url: 'http://localhost:8080/api/v1/students',
      type: 'GET',
      dataType: 'json',
      success: function (data) {
        displayStudents(data);
      }
    });
  }

  function searchStudents(keyword) {
    $.ajax({
      url: 'http://localhost:8080/api/v1/students/search',
      type: 'GET',
      data: { keyword: keyword },
      dataType: 'json',
      success: function (data) {
        displayStudents(data);
      }
    });
  }

  function displayStudents(students) {
    var tbody = $('#student-table-body');
    tbody.empty();

    if (students.length === 0) {
      var noDataMessage = $('<tr>').append($('<td colspan="5">').text('No students found.'));
      tbody.append(noDataMessage);
      return;
    }

    $.each(students, function (i, student) {
      var row = $('<tr>').attr('id', 'student-' + student.id);

      $('<td>').text(student.id).appendTo(row);
      $('<td>').text(student.name).appendTo(row);
      $('<td>').text(student.age).appendTo(row);
      $('<td>').text(student.courseName).appendTo(row);

      if (hasAdminAuthority()) {
        var editLink = $('<a>').attr({
          href: '/students/edit/' + student.id,
          class: 'btn btn-primary edit-link',
          'data-id': student.id
        }).text('Edit');

        var deleteButton = $('<button>').addClass('btn btn-danger delete-link').text('Delete')
          .click(function () {
            deleteStudent(student.id);
          });

        $('<td>').append(editLink, ' ', deleteButton).appendTo(row);
      }

      tbody.append(row);
    });

    // Sửa học viên - Xử lý sự kiện khi nhấp vào nút "Edit"
    $('.edit-link').click(function (e) {
      e.preventDefault();
      var studentId = $(this).data('id');
      editStudent(studentId);
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

  function editStudent(studentId) {
    window.location.href = '/students/edit/' + studentId;
  }

  function deleteStudent(id) {
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
});
