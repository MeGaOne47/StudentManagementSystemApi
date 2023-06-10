$(document).ready(function() {
  // Xử lý sự kiện khi nhấn nút "Update"
  $('#editStudentForm').submit(function(e) {
    e.preventDefault();

    // Lấy thông tin học viên từ form
    var studentId = $(this).attr('action').split('/').pop();
    var studentName = $('#name').val();
    var studentAge = $('#age').val();
    var studentCourse = $('#course').val();

    // Tạo đối tượng học viên mới
    var updatedStudent = {
      name: studentName,
      age: studentAge,
      courseName: studentCourse
    };

    // Gửi yêu cầu PUT để cập nhật học viên
    $.ajax({
      url: 'http://localhost:8080/api/v1/students/' + studentId,
      type: 'PUT',
      dataType: 'json',
      contentType: 'application/json',
      data: JSON.stringify(updatedStudent)
    })
      .then(function() {
        alert('Student updated successfully!');
        window.location.href = '/students'; // Chuyển hướng về trang danh sách học viên
      })
      .catch(function(xhr) {
      if(xhr.status === 200)
      {
            alert('Student updated successfully!');
            window.location.href = '/students';
      }
      });
  });
});
