$(document).ready(function() {
  // Khởi tạo validator cho form
  $('#addCourseForm').validate({
    rules: {
      name: {
        required: true,
        maxlength: 50
      },
      description: {
        required: true,
        maxlength: 100
      }
    },
    messages: {
      name: {
        required: "Name is required",
        maxlength: "Name must be less than 50 characters"
      },
      description: {
        required: "Description is required",
        maxlength: "Description must be less than 100 characters"
      }
    },
    errorPlacement: function (error, element) {
      error.appendTo(element.next('.error-message'));
    }
  });

  // Xử lý sự kiện khi nhấn nút "Add"
  $('#addCourseForm').submit(function(e) {
    e.preventDefault();

    // Kiểm tra tính hợp lệ của form
    if ($('#addCourseForm').valid()) {
      // Lấy thông tin khóa học từ form
      var courseName = $('#name').val();
      var courseDescription = $('#description').val();

      // Tạo đối tượng khóa học mới
      var newCourse = {
        name: courseName,
        description: courseDescription
      };

      // Gửi yêu cầu POST để thêm khóa học
      $.ajax({
        url: 'http://localhost:8080/api/v1/courses',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(newCourse),
        success: function() {
          alert('Course added successfully!');
          window.location.href = '/courses'; // Chuyển hướng về trang danh sách khóa học
        },
        error: function(xhr) {
          if (xhr.status === 200) {
            alert('Course added successfully!');
            window.location.href = '/courses';
          } else {
            alert('Error adding course.');
          }
        }
      });
    }
  });
});
