$(document).ready(function() {
  // Khởi tạo validator cho form
  $('#addClassForm').validate({
    rules: {
      name: {
        required: true
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
        required: "Please enter the class name."
      },
      description: {
        required: "Please enter the class description."
      },
      teacherName: {
        required: "Please select a teacher."
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
