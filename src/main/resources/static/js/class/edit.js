$(document).ready(function() {
  // Xử lý sự kiện khi nhấn nút "Update"
  $('#editClassForm').validate({
    rules: {
      name: {
        required: true,
        maxlength: 50
      },
      description: {
        required: true
      },
      teacherName: {
        required: true
      }
    },
    messages: {
      name: {
        required: '<span class="error-message">Please enter the class name.</span>',
        maxlength: '<span class="error-message">Please enter the class name (name <= 50 characters).</span>'
      },
      description: {
        required: '<span class="error-message">Please enter the class description.</span>'
      },
      teacherName: {
        required: '<span class="error-message">Please enter the teacher\'s name.</span>'
      }
    },
    errorPlacement: function(error, element) {
      error.appendTo(element.parent()); // Gắn thông báo lỗi vào phần tử cha của trường nhập liệu
    },
    submitHandler: function(form) {
      event.preventDefault();

      // Lấy thông tin lớp học từ form
      var classId = $(form).attr('action').split('/').pop();
      var className = $('#name').val();
      var classDescription = $('#description').val();
      var teacherName = $('#teacher').val();

      // Tạo đối tượng lớp học mới
      var updatedClass = {
        name: className,
        description: classDescription,
        teacherName: teacherName
      };

      // Gửi yêu cầu PUT để cập nhật lớp học
      $.ajax({
        url: 'http://localhost:8080/api/v1/classes/' + classId,
        type: 'PUT',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(updatedClass)
      })
        .then(function() {
          alert('Class updated successfully!');
          window.location.href = '/classes'; // Chuyển hướng về trang danh sách lớp học
        })
        .catch(function(xhr) {
          if (xhr.status === 200) {
            alert('Class updated successfully!');
            window.location.href = '/classes';
          }
        });
    }
  });
});
