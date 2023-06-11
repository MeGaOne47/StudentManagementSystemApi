$(document).ready(function() {
  // Khởi tạo validator cho form
  $('#addClassForm').validate({
    rules: {
      name: {
        required: true,
        maxlength: 50
      },
      teacher: {
        required: true
      },
      room: {
        required: true
      }
    },
    messages: {
      name: {
        required: "Class name is required",
        maxlength: "Class name must be less than 50 characters"
      },
      teacher: {
        required: "Teacher name is required"
      },
      room: {
        required: "Room number is required"
      }
    },
    errorPlacement: function (error, element) {
      error.appendTo(element.next('.error-message'));
    }
  });

  // Xử lý sự kiện khi nhấn nút "Add"
  $('#addClassForm').submit(function(e) {
    e.preventDefault();

    // Kiểm tra tính hợp lệ của form
    if ($('#addClassForm').valid()) {
      // Lấy thông tin lớp học từ form
      var className = $('#className').val();
      var classDescription = $('#classDescription').val();
      var teacherName = $('#teacher').val();

      // Tạo đối tượng lớp học mới
      var newClass = {
        name: className,
        description: classDescription,
        teacherName: teacherName
      };

      // Gửi yêu cầu POST để thêm lớp học
      $.ajax({
        url: '/api/v1/classes',
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(newClass),
        success: function() {
          alert('Class added successfully!');
          window.location.href = '/classes'; // Chuyển hướng về trang danh sách lớp học
        },
        error: function(xhr) {
          if (xhr.status === 200) {
            alert('Class added successfully!');
            window.location.href = '/classes';
          } else {
            alert('Error adding class.');
          }
        }
      });
    }
  });
});
