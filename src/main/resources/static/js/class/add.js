$(document).ready(function() {
  // Khởi tạo validator cho form
  $('#addClassForm').validate({
    rules: {
      className: {
        required: true,
        maxlength: 50
      },
      classDescription: {
        required: true,
        maxlength: 100
      }
    },
    messages: {
      className: {
        required: "Class Name is required",
        maxlength: "Class Name must be less than 50 characters"
      },
      classDescription: {
        required: "Class Description is required",
        maxlength: "Class Description must be less than 100 characters"
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
      var teacherId = $('#teacher').val(); // Lấy ID của giáo viên

      // Tạo đối tượng lớp học mới
      var newClass = {
        className: className,
        classDescription: classDescription,
        teacher: { id: teacherId } // Thêm thông tin giáo viên vào đối tượng lớp học
      };

      // Gửi yêu cầu POST để thêm lớp học
      $.ajax({
        url: 'http://localhost:8080/api/v1/classes',
        type: 'POST',
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
