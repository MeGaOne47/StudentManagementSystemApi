$(document).ready(function() {
  // Khởi tạo validator cho form
  $('#addTeacherForm').validate({
    rules: {
      name: {
        required: true,
        maxlength: 50
      },
      age: {
        required: true,
        min: 18
      },
      course: {
        required: true
      }
    },
    messages: {
      name: {
        required: "Name is required",
        maxlength: "Name must be less than 50 characters"
      },
      age: {
        required: "Age is required",
        min: "Age must be greater than or equal to 18"
      },
      course: {
        required: "Course is required"
      }
    },
    errorPlacement: function (error, element) {
      error.appendTo(element.next('.error-message'));
    }
  });

  // Xử lý sự kiện khi nhấn nút "Add"
  $('#addTeacherForm').submit(function(e) {
    e.preventDefault();

    // Kiểm tra tính hợp lệ của form
    if ($('#addTeacherForm').valid()) {
      // Lấy thông tin giáo viên từ form
      var teacherName = $('#name').val();
      var teacherAge = $('#age').val();
      var teacherCourse = $('#course').val();

      // Tạo đối tượng giáo viên mới
      var newTeacher = {
        name: teacherName,
        age: teacherAge,
        courseName: teacherCourse
      };

      // Gửi yêu cầu POST để thêm giáo viên
      $.ajax({
        url: 'http://localhost:8080/api/v1/teachers',
        type: 'POST',
        dataType: 'text', // Thêm dòng này để chỉ định kiểu dữ liệu trả về là JSON
        contentType: 'application/json',
        data: JSON.stringify(newTeacher),
        success: function() {
          alert('Teacher added successfully!');
          window.location.href = '/teachers'; // Chuyển hướng về trang danh sách giáo viên
        },
        error: function(xhr) {
          if (xhr.status === 200) {
            alert('Teacher added successfully!');
            window.location.href = '/teachers';
          } else {
            alert('Error adding teacher.');
          }
        }
      });
    }
  });
});
