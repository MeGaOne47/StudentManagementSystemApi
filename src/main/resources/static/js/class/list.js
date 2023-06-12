$(document).ready(function () {
  loadClasses();

  $('#searchInput').on('input', function () {
    var keyword = $(this).val();
    searchClasses(keyword);
  });

  function loadClasses() {
    $.ajax({
      url: 'http://localhost:8080/api/v1/classes',
      type: 'GET',
      dataType: 'json',
      success: function (data) {
        displayClasses(data);
      }
    });
  }

  function searchClasses(keyword) {
    $.ajax({
      url: 'http://localhost:8080/api/v1/classes/search',
      type: 'GET',
      data: { keyword: keyword },
      dataType: 'json',
      success: function (data) {
        displayClasses(data);
      }
    });
  }

  function displayClasses(classes) {
    var tbody = $('#class-table-body');
    tbody.empty();

    if (classes.length === 0) {
      var noDataMessage = $('<tr>').append($('<td colspan="5">').text('No classes found.'));
      tbody.append(noDataMessage);
      return;
    }

    $.each(classes, function (i, classItem) {
      var row = $('<tr>').attr('id', 'class-' + classItem.id);

      $('<td>').text(classItem.id).appendTo(row);
      $('<td>').text(classItem.name).appendTo(row);
      $('<td>').text(classItem.description).appendTo(row);
      $('<td>').text(classItem.teacherName).appendTo(row);

      if (hasAdminAuthority()) {
        var editLink = $('<a>').attr({
          href: '/classes/edit/' + classItem.id,
          class: 'btn btn-primary edit-link',
          'data-id': classItem.id
        }).text('Edit');

        var deleteButton = $('<button>').addClass('btn btn-danger delete-link').text('Delete')
          .click(function () {
            deleteClass(classItem.id);
          });

        $('<td>').append(editLink, '  ', deleteButton).appendTo(row);
      }

      tbody.append(row);
    });

    // Sửa lớp học - Xử lý sự kiện khi nhấp vào liên kết "Edit"
    $('.edit-link').click(function (e) {
      e.preventDefault();
      var classId = $(this).data('id');
      editClass(classId);
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

  function editClass(classId) {
    window.location.href = '/classes/edit/' + classId;
  }

  function deleteClass(id) {
    if (confirm('Are you sure you want to delete this class?')) {
      $.ajax({
        url: 'http://localhost:8080/api/v1/classes/' + id,
        type: 'DELETE',
        success: function () {
          alert('Class deleted successfully!');
          $('#class-' + id).remove();
        }
      });
    }
  }
});
