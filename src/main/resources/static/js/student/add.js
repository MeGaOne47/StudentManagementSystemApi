$(document).ready(function() {
  // Khởi tạo validator cho form
  $('#addStudentForm').validate({
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
  $('#addStudentForm').submit(function(e) {
    e.preventDefault();

    // Kiểm tra tính hợp lệ của form
    if ($('#addStudentForm').valid()) {
      // Lấy thông tin học viên từ form
      var studentName = $('#name').val();
      var studentAge = $('#age').val();
      var studentCourse = $('#course').val();

      // Tạo đối tượng học viên mới
      var newStudent = {
        name: studentName,
        age: studentAge,
        courseName: studentCourse
      };

    console.log(newStudent)
      // Gửi yêu cầu POST để thêm học viên
      $.ajax({
        url: 'http://localhost:8080/api/v1/students',
        type: 'POST',
        dataType: 'text', // Thêm dòng này để chỉ định kiểu dữ liệu trả về là JSON
        contentType: 'application/json',
        data: JSON.stringify(newStudent),
        success: function() {
          alert('Student added successfully!');
          window.location.href = '/students'; // Chuyển hướng về trang danh sách học viên
        },
        error: function(xhr) {
          if (xhr.status === 200) {
            alert('Student added successfully!');
            window.location.href = '/students';
          } else {
            alert('Error adding student.');
          }
        }
      });
    }
  });
});
